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
            errorMessage: '',
            items: [],
            inCartItems: [],
            checkedItems: [],
        };
    }

    clearState() {
        this.setState({
            name: '',
            address: '',
            postcode: '',
            comments: '',
            errorMessage: '',
            checkedItems: [],
        });
    }

    componentDidMount () {
        $.get('/session', (results) => {
            this.setState({
                userId: results
            });
            $.get('/api/cart/'+this.state.userId, (results) => {
                this.setState({inCartItems: results});
            });
            $.get('/api/items', items => {
                this.setState({items: items});
            })
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
        if (this.state.name === '' ||
            this.state.address === '' ||
            this.state.postcode === '') {
            this.setState({
                errorMessage: "You have to fill the fields"
            });
            return;
        }
        if (this.state.checkedItems.length == 0)  {
            this.setState({
                errorMessage: "you have to select at least one item"
            });
            return;
        }
        this.state.checkedItems.map(item => {
            $.ajax({
                type: 'POST',
                url: '/api/order',
                contentType: "application/json",
                dataType: 'json',
                data: JSON.stringify({
                    userId: parseInt(this.state.userId),
                    itemname: item.name,
                    name: this.state.name,
                    address: this.state.address,
                    postcode: this.state.postcode,
                    comments: this.state.comments
                }),
                success: ((data) => {
                    this.setState({
                        buttontext: "Paid!",
                        errorMessage: ""
                    });
                    $.ajax({
                        url: "/api/cart/"+this.state.userId+"/" + item.name,
                        type: 'DELETE',
                        success: (data) => {
                            this.setState({
                                inCartItems: $.grep(this.state.inCartItems, (i) => i.itemName !== item.name)
                            })
                        }
                    });
                }).bind(this)(),
                failure: (() => {
                    this.setState({
                        buttontext: "Error"
                    })
                }).bind(this)
            })
        });
        this.clearState.bind(this)();
    }

    handleCheckbox(itemName, checked) {
        let item = this.state.items.filter(item => item.name === itemName)[0];
        if (checked) {
            var newArray = this.state.checkedItems.slice();
            newArray.push(item);
            this.setState({checkedItems: newArray})
        } else {
            this.setState({
                checkedItems: $.grep(this.state.checkedItems, item => item.name !== itemName)
            })
        }
    }

    render() {
        let sum = this.state.checkedItems
            .map(item => item.price)
            .reduce((a,b) => a+b, 0);

        return (
            <div className="row">
                <Items items={this.state.inCartItems} onChange={this.handleCheckbox.bind(this)} />
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
                <div className="sum">Sum: <strong>{sum}</strong></div>
                <Submit onClick={this.submit.bind(this)} buttontext={this.state.buttontext} />
                <span style={{color: 'red'}}>{this.state.errorMessage}</span>
            </div>
        )
    }
}

class Items extends React.Component {
    constructor(props) {
        super(props)
    }

    handleChange(e) {
        this.props.onChange(e.target.value, e.target.checked);
    }

    render() {

        let checkboxes = this.props.items.map(item => {
            return (
                <div class="checkbox">
                    <label>
                        <input type="checkbox" onChange={this.handleChange.bind(this)} value={item.itemName} />
                        {item.itemName}
                    </label>
                </div>
            );
        });

        return (
            <div>
                {checkboxes}
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
