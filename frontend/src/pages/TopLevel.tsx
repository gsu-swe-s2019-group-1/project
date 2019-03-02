import React from "react"
import { BrowserRouter as Router, Route, Link, withRouter, RouteComponentProps } from "react-router-dom"
import { Layout, Menu, Icon } from 'antd'
import { UserPage } from "./UserPage";
const { Header, Content, Footer, Sider, } = Layout

interface State {
    sidebarCollapsed: boolean
}

const ROUTES = [
    { to: '/transactions', label: 'Transactions' }
]

const SiderMenu = withRouter(({ location }: RouteComponentProps<{}>) => (
    <Menu theme="dark" selectedKeys={[location.pathname]} mode="inline">
        {ROUTES.map(({ to, label }) => (
            <Menu.Item key={to}>
                <Link to={to}><span>{label}</span></Link>
            </Menu.Item>
        ))}
    </Menu>
))

export class TopLevel extends React.Component<{}, State> {
    constructor() {
        super({})
        this.state = {
            sidebarCollapsed: false
        }
    }

    onCollapse = (collapsed: boolean) => {
        this.setState({ sidebarCollapsed: collapsed });
    }

    render() {
        return (
            <Router>
                <Layout style={{ minHeight: '100vh' }}>
                    <Sider
                        collapsible
                        collapsed={this.state.sidebarCollapsed}
                        onCollapse={this.onCollapse}
                    >
                        <div className="logo" />
                        <SiderMenu />
                    </Sider>
                    <Layout>
                        <Content style={{ margin: '24px' }}>
                            <Route path="/transactions" component={UserPage} />
                        </Content>
                    </Layout>
                </Layout>
            </Router>)
    }
}