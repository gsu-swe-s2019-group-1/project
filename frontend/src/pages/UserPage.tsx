import { Client, UserTransactions, User, LedgerEntry, IUser } from "../models/api";
import React, { useState, useEffect, FC, useContext } from "react";
import { Row, Col, Card } from "antd";
import { UserTransactionTable } from "../components/UserTransactionTable";
import { MoneyTransferForm } from "../components/MoneyTransferForm";

const client = new Client()

export const UserPage: FC<{ userOrId: IUser | number }> = ({ userOrId }) => {
    const userId = typeof userOrId === 'number' ? userOrId : userOrId.id

    const [balance, setBalance] = useState<number>(0)
    const [transactions, setTransactions] = useState<LedgerEntry[]>([])
    const [user, setUser] = useState<IUser | null>(
        typeof userOrId === 'number' ? null : userOrId)

    useEffect(() => {
        if (typeof userOrId !== 'number')
            return
        client.getUserTransactions(userOrId).then(
            (data: UserTransactions) => {
                setBalance(data.balance)
                setTransactions(data.transactions)
                setUser(user)
            })
    }, [userOrId, setBalance, setTransactions])

    if (user == null) {
        return null
    }

    return (
        <React.Fragment>
            <Row>
                <Card className="headerInfo">
                    <Row>
                        <Col span={12}>
                            <span>Welcome</span>
                            <p>{user.name}!</p>
                            <em />
                        </Col>
                        <Col span={12}>
                            <span>Balance</span>
                            <p>${balance.toFixed(2)}</p>
                        </Col>
                    </Row>
                </Card>
            </Row>
            <Row>
                <MoneyTransferForm
                    balance={10}
                    buttonText="Send money"
                    canDeposit={false}
                    user={user} />
            </Row>
            <UserTransactionTable transactions={transactions} />
        </React.Fragment>
    )
}