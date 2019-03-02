import React from "react"
import { Route, Link, withRouter, RouteComponentProps, Redirect } from "react-router-dom"
import { Layout, Menu, Row } from 'antd'
import { UserPage } from "./UserPage"
import { LoginPage } from "./LoginPage"
import { User } from "../models/api"
import Alert, { AlertProps } from "antd/lib/alert"

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
}

const ROUTES: AppRoute[] = [
    { to: '/login', label: 'Login' },
    { to: '/transactions', label: 'Transactions' },
]

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

    onLogin = (user: User) => {
        document.cookie = `user:${JSON.stringify(user.toJSON())}`
        this.setState({ user })
    }
    onLogout = () => {
        document.cookie = ''
        this.setState({ user: undefined })
    }

    BoundLoginPage = () => (
        <LoginPage onLogin={this.onLogin} />
    )

    render() {
        const path = this.props.location.pathname

        if (this.state.user == null && path != '/login') {
            return <Redirect to={'/login'} />
        }
        if (this.state.user != null && path == '/login') {
            return <Redirect to={'/transactions'} />
        }

        return (
            <Layout style={{ minHeight: '100vh' }}>
                <Sider
                    collapsible
                    collapsed={this.state.sidebarCollapsed}
                    onCollapse={this.onCollapse}
                >
                    <div className="logo" />
                    <Menu theme="dark" selectedKeys={[path]} mode="inline">
                        {ROUTES
                            .filter(({ to }) =>
                                (path == '/login' && to == '/login') ||
                                (path != '/login' && to != '/login'))
                            .map(({ to, label }) => (
                                <Menu.Item key={to}>
                                    <Link to={to}><span>{label}</span></Link>
                                </Menu.Item>
                            ))}
                        {path != '/login' ? [
                            <Menu.Divider />,
                            <Menu.Item onClick={this.onLogout}>Log out</Menu.Item>
                        ] : null}
                    </Menu>
                </Sider>
                <Layout>
                    <Content style={{ margin: '24px' }}>
                        <Row>
                            {this.state.alerts.map((props) =>
                                <Alert closable showIcon {...props} />
                            )}
                        </Row>
                        <Route path="/login" component={this.BoundLoginPage} />
                        <UserContext.Provider value={this.state.user!}>
                            <AlertContext.Provider value={this.state.alerts}>
                                <Route path="/transactions" component={UserPage} />
                            </AlertContext.Provider>
                        </UserContext.Provider>
                    </Content>
                </Layout>
            </Layout>)
    }
}

export const TopLevel = withRouter(TopLevelInner)