import { Client, AccountType, User } from "../models/api";
import React, { useState, useCallback, FC } from "react";
import { Form, Icon, Input, Button, Drawer, Select } from "antd";
import { FormComponentProps } from "antd/lib/form";
import { AlertContext } from "../pages/TopLevel";

const client = new Client()

const { Option } = Select;

const UserCreationInner: FC<FormComponentProps> = ({ form }) => {
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
                client.createUser(new User(values)).then((user) => {
                    alertContext.addAlert({
                        message: 'Successfully created user!'
                    })
                })
            }
        });
    }, [onClose])

    return (
        <React.Fragment>
            <Button onClick={showDrawer}>Create user</Button>
            <Drawer
                title="Create user"
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
                        {getFieldDecorator('name', {
                            rules: [{ required: true, message: "Please enter the user's name" }],
                        })(
                            <Input prefix={<Icon type="inbox" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Name" />
                        )}
                    </Form.Item>
                    <Form.Item>
                        {getFieldDecorator('ssn', {
                            rules: [{ required: true, message: "Enter the user's tax ID" },],
                        })(
                            <Input prefix={<Icon type="file-text" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Tax id" />
                        )}
                    </Form.Item>
                    <Form.Item>
                        {getFieldDecorator('permissions', {
                            rules: [{ required: true, message: 'Please assign the user a set of permissions' },],
                        })(
                            <Select style={{ color: 'rgba(0,0,0,.25)' }} placeholder="Account type">
                                {Object.keys(AccountType)
                                    .filter(key => typeof key !== 'number')
                                    .map(key =>
                                        <Option key={key} value={AccountType[key as any]}>{key}</Option>
                                    )}
                            </Select>
                        )}
                    </Form.Item>
                    <Form.Item>
                        {getFieldDecorator('username', {
                            rules: [{ required: true, message: "Please create a username for the user" }],
                        })(
                            <Input prefix={<Icon type="inbox" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Username" />
                        )}
                    </Form.Item>
                    <Form.Item>
                        {getFieldDecorator('password', {
                            rules: [{ required: true, message: 'Please assign the user a password' },],
                        })(
                            <Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="Password" />
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
                            Create user
                        </Button>
                    </div>
                </Form>
            </Drawer>
        </React.Fragment>
    );
}

export const UserCreationForm = Form.create()(UserCreationInner);
