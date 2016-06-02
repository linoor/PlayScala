import React from 'react';
import {render} from 'react-dom';

class Order extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: 'test',
            address: 'test',
            postcode: 'test',
            comments: 'test'
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
                <Name name={this.state.name} onChange={this.inputOnChange("name").bind(this)} />
                <Address address={this.state.address} />
                <Postcode postcode={this.state.postcode} />
                <Comments comments={this.state.comments} />
                <Submit />
            </div>
        )
    }
}

class Name extends React.Component {
    constructor(props) {
        super(props)
    }

    handleChange() {
        this.props.onChange(
            this.refs.name.value
        )
    }

    render() {
        return (
            <div className="control-group">
                <label className="control-label" for="username">Name</label>
                <div className="controls">
                    <input type="text" id="name" name="name"
                           ref="name"
                           value={this.props.name}
                           onChange={this.handleChange.bind(this)}
                           placeholder="Your Name..." className="form-control input-lg" required/>
                </div>
            </div>
        )
    }
}

class Address extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div className="control-group">
                <label className="control-label" for="username">Address</label>
                <div className="controls">
                    <input type="text" id="name" name="name" value={this.props.address}
                           placeholder="Your address..." className="form-control input-lg" required/>
                </div>
            </div>
        )
    }
}

class Postcode extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div className="control-group">
                <label className="control-label" for="username">Postcode</label>
                <div className="controls">
                    <input type="text" id="name" name="name" value={this.props.postcode}
                           placeholder="Your postcode..." className="form-control input-lg" required/>
                </div>
            </div>
        )
    }
}

class Comments extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div className="control-group">
                <label className="control-label" for="username">Additional comment</label>
                <div className="controls">
                    <input type="text" id="name" name="name" value={this.props.comments}
                           placeholder="Additional info? (size, num of objects etc.)"
                           className="form-control input-lg"/>
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
