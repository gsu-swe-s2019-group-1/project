import { Client, User } from "../models/api";
import React, { FC, useCallback } from "react";
import { Form, Icon, Input, Button, } from "antd";
import { FormComponentProps } from "antd/lib/form";

const client = new Client()

export interface LoginPageProps {
    onLogin: (user: User) => void
}

export const LoginPageInner: FC<LoginPageProps & FormComponentProps> = ({
    form, onLogin
}) => {
    const { getFieldDecorator } = form;

    const handleSubmit: React.FormEventHandler<any> = useCallback((e) => {
        e.preventDefault();
        form!.validateFields((err, values) => {
            if (!err) {
                client.loginUser(values)
                    .then((data) => onLogin(data))
            }
        });
    }, [form, onLogin])

    return (
        <Form onSubmit={handleSubmit}>
            <Form.Item>
                {getFieldDecorator('username', {
                    rules: [{ required: true, message: 'Please input your username!' }],
                })(
                    <Input prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />} placeholder="Username" />
                )}
            </Form.Item>
            <Form.Item>
                {getFieldDecorator('password', {
                    rules: [{ required: true, message: 'Please input your Password!' }],
                })(
                    <Input prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />} type="password" placeholder="Password" />
                )}
            </Form.Item>
            <Form.Item>
                <Button type="primary" htmlType="submit">
                    Log in
                    </Button>
            </Form.Item>
        </Form>
    );
}

export const LoginPage: React.ComponentType<LoginPageProps> = Form.create<LoginPageProps>()(LoginPageInner);