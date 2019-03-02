import React, { FC } from 'react';
import { IUser } from '../models/api';
import { Table } from 'antd'
import { Link } from 'react-router-dom';

export interface UserTransactionTableProps {
    users: (IUser & { balance: number })[]
}

export const UserTable: FC<UserTransactionTableProps> = ({
    users
}) => <Table
        columns={[{
            title: 'Name',
            dataIndex: 'name',
            key: 'name',
            sorter: true,
            render: (text, user) => <Link to={`/transactions/${user.id}`}>{text}</Link>
        }, {
            title: 'Username',
            dataIndex: 'username',
            key: 'username',
            sorter: true,
        }, {
            title: 'SSN',
            dataIndex: 'ssn',
            key: 'ssn',
            sorter: true,
            width: '10em',
        },{
            title: 'Balance ($)',
            dataIndex: 'balance',
            key: 'balance',
            sorter: true,
            width: '10em',
            align: 'right',
            render: (val: number) => val.toFixed(2),
        },]}
        dataSource={users}
        rowKey="id" />