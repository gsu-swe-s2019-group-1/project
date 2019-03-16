import { Client, User, AccountType, IUser } from "../models/api";
import React, { useState, useEffect, FC } from "react";
import { UserTable } from "../components/UserTable";
import { UserCreationForm } from "../components/UserCreationForm";
import { Row } from "antd";
import { AlertContext } from "./TopLevel";

const client = new Client()

export const CustomerListPage: FC = () => {
    const [customers, setCustomers] = useState<IUser[]>([])
    const alertContext = React.useContext(AlertContext)
    const [customersAndBalance, setCustomersAndBalance] = useState<(IUser & { balance: number })[]>([])


    useEffect(() => {
        client.getUserList(AccountType.Customer).then(
            (data: User[]) => {
                const values = data.map(customer =>
                    ({ ...customer, balance: 0 }))
                setCustomersAndBalance(values)
                setCustomers(values)
            })
    }, [setCustomers, alertContext])

    useEffect(() => {
        customers.forEach((user) => {
            client.getUserTransactions(user.id).then((transactions) => {
                setCustomersAndBalance(customersAndBalance.map(customer => {
                    if (customer.id != user.id) {
                        return customer
                    }

                    return { ...customer, balance: transactions.balance }
                }))
            })
        })
    }, [setCustomers, customers])

    return (
        <React.Fragment>
            <Row><UserCreationForm /></Row>
            <UserTable users={customersAndBalance} />
        </React.Fragment>
    )
}