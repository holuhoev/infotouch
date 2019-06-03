import React from "react";
import { withRouter } from "react-router-dom";
import { Button, Form, Input, Spin } from "antd";
import { login } from "../../../utils/auth";
import { message } from "antd";
import { ROUTE } from "../../../utils/routes";

class LoginPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            login:     '',
            password:  '',
            isLoading: false,
        };
    }


    handleEdit = (e) => {
        const value = e.target.value;
        const field = e.target.name;

        if (field) {
            this.setState({ [ field ]: value })
        }
    };

    handleSubmit = () => {

        this.setState({
            isLoading: true
        });

        login(this.state.login, this.state.password)
            .then(() => {
                this.setState({
                    isLoading: false
                });

                const fromUrl = this.props.location.state && this.props.location.state.from ? this.props.location.state.from.pathname : ROUTE.MAP;
                this.props.history.push(fromUrl);
            })
            .catch(error => {
                this.setState({
                    isLoading: false
                });

                message.error('Ошибка авторизации');
            })
    };

    render() {
        return (
            <article className="page-login">
                <Spin spinning={ this.state.isLoading }>
                    <Form layout="vertical" onChange={ this.handleEdit }>
                        <Form.Item>
                            <Input name={ "login" } addonBefore={ "Логин" } value={ this.state.login }/>
                        </Form.Item>
                        <Form.Item>
                            <Input name={ "password" } type={ "password" } addonBefore={ "Пароль" }
                                   value={ this.state.password }/>
                        </Form.Item>
                        <Button
                            onClick={ this.handleSubmit }
                        >
                            Войти
                        </Button>
                    </Form>
                </Spin>

            </article>
        )
    }
}

export default withRouter(LoginPage);