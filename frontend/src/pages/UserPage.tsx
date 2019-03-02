import { Client, IUserTransactions, UserTransactions } from "../models/api";
import React from "react";
import { Row, Col, Card } from "antd";
import { UserTransactionTable } from "../components/UserTransactionTable";

const client = new Client()

export class UserPage extends React.Component<{}, IUserTransactions> {
    constructor() {
        super({});
        this.state = {
            balance: 0,
            transactions: []
        }
    }
    componentDidMount() {
        client.getUserTransactions(0).then(
            (data: UserTransactions) => this.setState(data))
    }

    render() {
        return (
            <div>
                <Row>
                    <Col span={24}>
                        <Card className="headerInfo">
                            <span>Balance</span>
                            <p>${this.state.balance.toFixed(2)}</p>
                        </Card>
                    </Col>
                </Row>
                <UserTransactionTable transactions={this.state.transactions} />
            </div>)
    }
}