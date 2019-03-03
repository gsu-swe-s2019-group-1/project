import { Client, Body2, IUser } from "../models/api";
import React, { useState, useCallback, FC } from "react";
import { Form, Icon, Input, Button, Drawer, } from "antd";
import { FormComponentProps } from "antd/lib/form";
import { AlertContext } from "../pages/TopLevel";

export interface MoneyTransferFormProps {
    buttonText: string
    balance: number
    canDeposit: boolean
    user: IUser
}
const client = new Client()

const MoneyTransferInner: FC<MoneyTransferFormProps & FormComponentProps> = ({
    form,
    user,
    buttonText,
    canDeposit,
    balance,
}) => {
    const { getFieldDecorator } = form;

    const [drawerVisible, setDrawerVisible] = useState(false)
    const alertContext = React.useContext(AlertContext)


    const showDrawer = useCallback(() => {
        setDrawerVisible(true)
    }, [setDrawerVisible]);

    const onClose = useCallback(() => {
        setDrawerVisible(false)
    }, [setDrawerVisible]);

    const handleSubmit: React.FormEventHandler<any> = useCallback((e) => {
        e.preventDefault();
        form.validateFields((err, values) => {
            if (!err) {
                onClose()
                client.createUserTransaction(user.id, new Body2({
                    merchant: values.merchant,
                    amount: values.amount,
                })).then((transaction) => {
                    alertContext.addAlert({
                        message: `Successfully sent \$${transaction.amount} to ${transaction.merchant}`
                    })
                })
            }
        });
    }, [onClose])

    return (
        <React.Fragment>
            <Button onClick={showDrawer}>{buttonText}</Button>
            <Drawer
                title={buttonText}
                width={720}
                onClose={onClose}
                visible={drawerVisible}
                style={{
                    overflow: 'auto',
                    height: 'calc(100% - 108px)',
                    paddingBottom: '108px',
                }}
            >
                <Form layout="vertical" onSubmit={handleSubmit}>
                    <Form.Item>
                        {getFieldDecorator('merchant', {
                            rules: [{ required: true, message: 'Please add a destination' }],
                        })(
                            <Input prefix={<Icon type="inbox" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Destination" />
                        )}
                    </Form.Item>
                    <Form.Item>
                        {getFieldDecorator('amount', {
                            getValueFromEvent: (e: React.FormEvent<HTMLInputElement>) => {
                                const convertedValue = Number(e.currentTarget.value);
                                if (isNaN(convertedValue)) {
                                    return Number(form.getFieldValue("amount"));
                                } else {
                                    return convertedValue;
                                }
                            },
                            rules: [{
                                required: true,
                                message: 'Please add an amount to transfer'
                            }, {
                                validator: (rule, value, callback) =>
                                    callback(value <= balance ? [] : ["You can't transfer more money than you have"]),
                            }, {
                                validator: (rule, value, callback) =>
                                    callback(canDeposit || value > 0.01 ? [] : ["You can only transfer money out of your account"]),
                            },],
                        })(
                            <Input
                                prefix={<Icon type="dollar" style={{ color: 'rgba(0,0,0,.25)' }} />}
                                type="number"
                                placeholder="Amount" />
                        )}
                    </Form.Item>
                    <div style={{
                        position: 'absolute',
                        left: 0,
                        bottom: 0,
                        width: '100%',
                        borderTop: '1px solid #e9e9e9',
                        padding: '10px 24px',
                        background: '#fff',
                        textAlign: 'right',
                    }}>
                        <Button onClick={onClose} style={{ marginRight: 8 }}>
                            Cancel
                            </Button>
                        <Button type="primary" htmlType="submit">
                            Transfer
                            </Button>
                    </div>
                </Form>
            </Drawer>
        </React.Fragment>
    );
}

export const MoneyTransferForm = Form.create()(MoneyTransferInner);
