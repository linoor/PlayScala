import React from 'react';
import {render} from 'react-dom';

class Order extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            address: '',
            postcode: '',
            comments: ''
        };
    }

    inputOnChange(inputName) {
        return (value) => {
            let newState = {};
            newState[inputName] = value;
            this.setState(newState);
        }
    }

    render() {
        return (
            <div className="row">
                <Input value={this.state.name}
                       onChange={this.inputOnChange("name").bind(this)}
                       description="Name"
                       placeholder="Your name..."
                       name="name"/>
                <Input value={this.state.address}
                       onChange={this.inputOnChange("address").bind(this)}
                       description="Address"
                       placeholder="Your address..."
                       name="address"/>
                <Input value={this.state.postcode}
                       onChange={this.inputOnChange("postcode").bind(this)}
                       description="Postcode"
                       placeholder="Your postcode..."
                       name="postcode"/>
                <Input value={this.state.comments}
                       onChange={this.inputOnChange("comments").bind(this)}
                       description="Comments"
                       placeholder="Any additional info? (size, number of items etc.)"
                       name="comments"/>
                <Submit />
            </div>
        )
    }
}

class Input extends React.Component {
    constructor(props) {
        super(props)
    }

    handleChange() {
        this.props.onChange(
            this.refs.input.value
        )
    }

    render() {
        return (
            <div className="control-group">
                <label className="control-label" for="username">{this.props.description}</label>
                <div className="controls">
                    <input type="text" name={this.props.name}
                           ref="input"
                           value={this.props.value}
                           onChange={this.handleChange.bind(this)}
                           placeholder={this.props.placeholder} className="form-control input-lg" required/>
                </div>
            </div>
        )
    }
}

class Submit extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div className="control-group">
                <div className="controls">
                    <button type="submit" className="btn btn-success">Pay Now!</button>
                </div>
            </div>
        )
    }
}

render(<Order/>, document.getElementById('order'));
