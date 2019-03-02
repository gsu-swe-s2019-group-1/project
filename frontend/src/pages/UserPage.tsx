import { Client, IUserTransactions, UserTransactions, User } from "../models/api";
import React from "react";
import { Row, Col, Card, Button } from "antd";
import { UserTransactionTable } from "../components/UserTransactionTable";
import { MoneyTransferForm } from "../components/MoneyTransferForm";

const client = new Client()

export interface UserPageProps {
    user: User
}

export class UserPage extends React.Component<UserPageProps, IUserTransactions> {
    constructor(props: UserPageProps) {
        super(props);
        this.state = {
            balance: 0,
            transactions: []
        }
    }

    componentDidMount() {
        client.getUserTransactions(this.props.user.id).then(
            (data: UserTransactions) => this.setState(data))
    }

    render() {
        return (
            <React.Fragment>
                <Row>
                    <Card className="headerInfo">
                        <Row>
                            <Col span={12}>
                                <span>Welcome</span>
                                <p>{this.props.user.name}!</p>
                                <em />
                            </Col>
                            <Col span={12}>
                                <span>Balance</span>
                                <p>${this.state.balance.toFixed(2)}</p>
                            </Col>
                        </Row>
                    </Card>
                </Row>
                <Row>
                    <MoneyTransferForm
                        balance={10}
                        buttonText="Send money"
                        canDeposit={false}
                        user={this.props.user} />
                </Row>
                <UserTransactionTable transactions={this.state.transactions} />
            </React.Fragment>)
    }
}