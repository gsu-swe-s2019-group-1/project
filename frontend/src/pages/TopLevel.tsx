import React, { FC } from "react"
import { Route, Link, withRouter, RouteComponentProps, Redirect } from "react-router-dom"
import { Layout, Menu, Row } from 'antd'
import { UserPage } from "./UserPage"
import { LoginPage, LogoutPage } from "./LoginPage"
import { User } from "../models/api"
import Alert, { AlertProps } from "antd/lib/alert"
import { CustomerListPage } from "./CustomerListPagePage";

const { Content, Sider } = Layout

export type AlertContextValue = { addAlert: (props: AlertProps) => void } & AlertProps[]

interface State {
    sidebarCollapsed: boolean,
    user?: User
    alerts: AlertContextValue
}

interface AppRoute {
    to: string
    label: string
    showMenuIf: (path: string, user?: User) => boolean
    component: React.ComponentType<RouteComponentProps<any>> | React.ComponentType<any>;
}

export const AlertContext = React.createContext<AlertContextValue>(undefined as any)
export const UserContext = React.createContext<User>(undefined as any)

class TopLevelInner extends React.Component<React.PropsWithChildren<RouteComponentProps>, State> {
    constructor(props: React.PropsWithChildren<RouteComponentProps>) {
        super(props)
        let user = undefined
        try {
            user = JSON.parse(document.cookie.split("user:")[1])
        } catch (SyntaxError) {
            // user not logged in
        }

        this.state = {
            sidebarCollapsed: false,
            user: user,
            alerts: Object.assign([],
                { addAlert: this.addAlert }),
        }
    }

    addAlert = (props: AlertProps) => {
        this.setState({
            alerts: Object.assign(
                this.state.alerts.concat([props]),
                { addAlert: this.addAlert })
        })
    }

    onCollapse = (collapsed: boolean) => {
        this.setState({ sidebarCollapsed: collapsed });
    }

    onLogin = (user: User | undefined) => {
        if (user == null) {
            document.cookie = ''
        } else {
            document.cookie = `user:${JSON.stringify(user.toJSON())}`
        }
        this.setState({ user })
    }

    BoundLoginPage: FC = () => (
        <LoginPage onLogin={this.onLogin} />
    )
    BoundLogoutPage: FC = () => (
        <LogoutPage onLogin={this.onLogin} />
    )
    BoundTransactionList: FC = () => (
        <UserPage userOrId={this.state.user!} />
    )

    render() {
        const path = this.props.location.pathname

        if (this.state.user == null && path != '/login') {
            return <Redirect to={'/login'} />
        }
        if (this.state.user != null && path == '/login') {
            return <Redirect to={'/transactions'} />
        }

        const routes: AppRoute[] = [
            {
                to: '/login', label: 'Login',
                showMenuIf: (path, user) => user == null,
                component: this.BoundLoginPage,
            },
            {
                to: '/transactions', label: 'Transactions',
                showMenuIf: (path, user) => user != null,
                component: this.BoundTransactionList,
            },
            {
                to: '/transactions/:userId', label: 'Transactions',
                showMenuIf: () => false,
                component: (({ match }: RouteComponentProps<{ userId: string }>) =>
                    <UserPage userOrId={parseInt(match.params.userId)} />),
            },
            {
                to: '/customers', label: 'Customers',
                showMenuIf: (path, user) => user != null,
                component: CustomerListPage,
            },
            {
                to: '/logout', label: 'Log out',
                showMenuIf: (path, user) => user != null,
                component: this.BoundLogoutPage,
            },
        ]

        return (
            <Layout style={{ minHeight: '100vh' }}>
                <Sider
                    collapsible
                    collapsed={this.state.sidebarCollapsed}
                    onCollapse={this.onCollapse}
                >
                    <div className="logo" />
                    <Menu theme="dark" selectedKeys={[path]} mode="inline">
                        {routes
                            .filter(({ showMenuIf }) =>
                                showMenuIf(path, this.state.user))
                            .map(({ to, label }) => (
                                <Menu.Item key={to}>
                                    <Link to={to}><span>{label}</span></Link>
                                </Menu.Item>
                            ))}
                    </Menu>
                </Sider>
                <Layout>
                    <Content style={{ margin: '24px' }}>
                        <Row>
                            {this.state.alerts.map((props) =>
                                <Alert closable showIcon {...props} />
                            )}
                        </Row>
                        <UserContext.Provider value={this.state.user!}>
                            <AlertContext.Provider value={this.state.alerts}>
                                {routes.map(({ to, component }) =>
                                    <Route key={to} path={to} component={component} />)}
                            </AlertContext.Provider>
                        </UserContext.Provider>
                    </Content>
                </Layout>
            </Layout>)
    }
}

export const TopLevel = withRouter(TopLevelInner)