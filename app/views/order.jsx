import React from 'react';
import {render} from 'react-dom';

class Order extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="row">
                <Name />
                <Address />
                <Postcode />
                <Comments />
                <Submit />
            </div>
        )
    }
}

class Name extends React.Component {
    constructor(props) {
        super(props)
    }

    render() {
        return (
            <div className="control-group">
                <label className="control-label" for="username">Name</label>
                <div className="controls">
                    <input type="text" id="name" name="name"
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
                    <input type="text" id="name" name="name"
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
                    <input type="text" id="name" name="name"
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
                    <input type="text" id="name" name="name"
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
