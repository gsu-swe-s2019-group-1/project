import React from 'react';
import { LedgerEntry, Client, Anonymous } from '../models/api';

interface State {
    transactions: LedgerEntry[]
}

const client = new Client()

export class TransactionTable extends React.Component<{}, State> {
    constructor(props: {}) {
        super(props)
        this.state = {
            transactions: []
        }
    }

    componentDidMount() {
        client.getUserTransactions(0)
            .then((parts: Anonymous) => {
                this.setState({ transactions: parts.transactions })
            })
    }

    render() {
        return (
            <Table
                columns={[{
                    title: 'Name',
                    dataIndex: 'name',
                    key: 'name',
                    sorter: true,
                    filtered: false,
                }]}
                dataSource={this.state.parts.map((part: Part) => {
                    return { key: part.url!, name: part.part_number }
                })} />
        );
    }
}