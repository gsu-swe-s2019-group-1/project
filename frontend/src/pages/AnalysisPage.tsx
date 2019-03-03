import { Client, DailyTransactions } from "../models/api";
import React, { FC, useEffect, useState } from "react";
import { Row, Card, Col, Skeleton, Table, } from "antd";

const client = new Client()

export const AnalysisPage: FC = () => {
    const [transactionData, setTransactionData] = useState<DailyTransactions>()

    useEffect(() => {
        client.getDayTransactions(new Date()).then(
            data => setTransactionData(data))
    }, [setTransactionData])

    if (transactionData == null)
        return <Skeleton active />

    return (
        <React.Fragment>
            <Row>
                <Card className="headerInfo">
                    <Row>
                        <Col span={12}>
                            <span>Current date</span>
                            <p>{(new Date()).toLocaleDateString()}</p>
                            <em />
                        </Col>
                        <Col span={12}>
                            <span>Transaction volume</span>
                            <p>${transactionData.cashFlow.toFixed(2)}</p>
                        </Col>
                    </Row>
                </Card>
            </Row>
            <Row>
                <Table
                    columns={[{
                        title: 'User Id',
                        dataIndex: 'userId',
                        key: 'userId',
                        sorter: true,
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
                    dataSource={transactionData.transactions}
                    rowKey="id" />
            </Row>
        </React.Fragment>
    );
}