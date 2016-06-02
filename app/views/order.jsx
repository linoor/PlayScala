import React from 'react';
import {render} from 'react-dom';

class Order extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            address: '',
            postcode: '',
            comments: '',
            buttontext: 'Pay Now!',
        };
    }

    componentDidMount () {
        $.get('/session', (results) => {
            this.setState({
                userId: results
            });
            $.get('/api/cart/'+this.state.userId, (results) => {
                let cartEntryItems = results.map(i => i.itemName);
                this.setState({
                    items: cartEntryItems
                })
            });
        })
    }

    inputOnChange(inputName) {
        return (value) => {
            let newState = {};
            newState[inputName] = value;
            this.setState(newState);
        }
    }

    submit() {
        this.state.items.map((item) => {
            $.ajax({
                type: 'POST',
                url: '/api/order',
                contentType: "application/json",
                dataType: 'json',
                data: JSON.stringify({
                    userId: parseInt(this.state.userId),
                    itemname: item,
                    name: this.state.name,
                    address: this.state.address,
                    postcode: this.state.postcode,
                    comments: this.state.comments,
                }),
                success: (result) => {
                    this.setState({
                        buttontext: "Paid!"
                    })
                },
                failure: () => {
                    this.setState({
                        buttontext: "Error"
                    })
                }
            })
        })
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
                <Submit onClick={this.submit.bind(this)} buttontext={this.state.buttontext} />
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
                    <button onClick={this.props.onClick} type="submit" className="btn btn-success">{this.props.buttontext}</button>
                </div>
            </div>
        )
    }
}

render(<Order/>, document.getElementById('order'));
