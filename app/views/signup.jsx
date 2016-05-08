import React from 'react';
import {render} from 'react-dom';

class SignupForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            passwordError: '',
            confirmError: '',
            pass: '',
            passValidated: '',
            email: '',
            fullname: '',
            resultMessage: ''
        };
        this.validatePass = this.validatePass.bind(this)
        this.validateConfirm = this.validateConfirm.bind(this)
        this.register = this.register.bind(this)
        this.linkInput = this.linkInput.bind(this)
    }

    linkInput (key) {
        return (e) => {
            let state = {};
            state[key] = e.target.value;
            this.setState(state);
        }
    }

    validateConfirm (event) {
        let confirm = event.target.value;
        if (confirm !== this.state.pass) {
            this.setState({
                confirmError: "The passwords don't match",
                passValidated: ''
            })
        } else {
            this.setState({
                confirmError: "",
                pass: confirm,
                passValidated: this.state.pass
            })
        }
    }

    validatePass (event) {
        let pass = event.target.value;
        if (pass.length < 6) {
            this.setState({
                passwordError: 'Password should be at least 6 characters',
                passValidated: ''
            })
        } else {
            this.setState({
                pass: pass,
                passwordError: ''
            })
        }
    }

    register (e) {
        e.preventDefault();
        if (this.state.pass !== '' && this.state.fullname !== '' && this.state.email !== '') {
            $.ajax({
                url: '/api/user',
                type: "POST",
                data: JSON.stringify({
                    email: this.state.email,
                    fullname: this.state.fullname,
                    password: this.state.pass
                }),
                contentType: "application/json; charset=utf-8",
                processData: false,
                success: (result) => {
                    this.setState({
                        resultMessage: 'User has been created!'
                    })
                }
            });
        }
    }

    render () {
        return (
            <div className="row">
                <div className="col-md-6">
                    <form onSubmit={this.register} className="form-horizontal">
                        <fieldset>
                            <div id="legend">
                                <legend className="">Register</legend>
                            </div>
                            <div className="control-group">
                                <label className="control-label" for="username">Username</label>
                                <div className="controls">
                                    <input onChange={this.linkInput('fullname')} type="text" id="username" name="username" placeholder="" className="form-control input-lg" required/>
                                        <p className="help-block">Username can contain any letters or numbers, without spaces</p>
                                </div>
                            </div>

                            <div className="control-group">
                                <label className="control-label" for="email">E-mail</label>
                                <div className="controls">
                                    <input onChange={this.linkInput('email')} type="email" id="email" name="email" placeholder="" className="form-control input-lg" required/>
                                        <p className="help-block">Please provide your E-mail</p>
                                </div>
                            </div>

                            <div className="control-group">
                                <label className="control-label" for="password">Password</label>
                                <div className="controls">
                                    <input onChange={this.validatePass} type="password" id="password" name="password" placeholder="" className="form-control input-lg" required/>
                                        <p className="help-block">{this.state.passwordError}</p>
                                </div>
                            </div>

                            <div className="control-group">
                                <label className="control-label" for="password_confirm">Password (Confirm)</label>
                                <div className="controls">
                                    <input onChange={this.validateConfirm} type="password" id="password_confirm" name="password_confirm" placeholder="" className="form-control input-lg" required/>
                                        <p className="help-block">{this.state.confirmError}</p>
                                </div>
                            </div>

                            <div className="control-group">
                                <div className="controls">
                                    <button type="submit" className="btn btn-success">Register</button>
                                </div>
                            </div>
                            <p>{this.state.resultMessage}</p>
                        </fieldset>
                    </form>
                </div>
            </div>
        )
    }
}

render(<SignupForm/>, document.getElementById('signup'));
