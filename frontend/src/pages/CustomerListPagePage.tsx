import { Client, User, AccountType, IUser } from "../models/api";
import React, { useState, useEffect, FC } from "react";
import { UserTable } from "../components/UserTable";
import { UserCreationForm } from "../components/UserCreationForm";
import { Row } from "antd";
import { AlertContext } from "./TopLevel";

const client = new Client()

export const CustomerListPage: FC = () => {
    const [customers, setCustomers] = useState<(IUser & { balance: number })[]>([])
    const alertContext = React.useContext(AlertContext)

    useEffect(() => {
        client.getUserList(AccountType.Customer).then(
            (data: User[]) => {
                setCustomers(data.map(customer =>
                    ({ ...customer, balance: 0 })))
            })
    }, [setCustomers, alertContext])

    useEffect(() => {
        customers.forEach((user) => {
            client.getUserTransactions(user.id).then((transactions) => {
                setCustomers(customers.map(customer => {
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
            <UserTable users={customers} />
        </React.Fragment>
    )
}