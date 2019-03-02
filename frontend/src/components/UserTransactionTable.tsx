import React, { FC } from 'react';
import { LedgerEntry } from '../models/api';
import { Table } from 'antd'

export interface UserTransactionTableProps {
    transactions: LedgerEntry[]
}

export const UserTransactionTable: FC<UserTransactionTableProps> = ({
    transactions
}) => <Table
        columns={[{
            title: 'Date',
            dataIndex: 'dateTime',
            key: 'dateTime',
            sorter: true,
            render: (val: Date) => val.toLocaleDateString(),
            width: '10em'
        }, {
            title: 'Merchant',
            dataIndex: 'merchant',
            key: 'merchant',
            sorter: true,
        }, {
            title: 'Amount ($)',
            dataIndex: 'amount',
            key: 'amount',
            sorter: true,
            width: '10em',
            align: 'right',
            render: (val: number) => val.toFixed(2),
        },]}
        dataSource={transactions} />