import React from 'react';
import {render} from 'react-dom';

class Item extends React.Component {
    constructor(props) {
        super(props);
        this.removeFromCart = this.removeFromCart.bind(this);
        this.render = this.render.bind(this);
        this.state = {
            inCart: true
        };
    }

    removeFromCart() {
        if (this.state.inCart) {
            $.ajax({
                url: "/api/cart/"+this.props.userId+"/" + this.props.item.name,
                type: 'DELETE',
                success: (results) => {
                    this.setState({
                        inCart: false
                    })
                }
            })
        }
    }

    render () {
        let cartMessage = this.state.inCart ? "Remove from cart" : "Removed from cart";
        let disabled = !this.state.inCart;
        let src = "http://loremflickr.com/g/320/320/" + this.props.item.category + '?random=' + this.props.num;

        return (
            <div className="col-xs-12 col-md-6 item">
                <div className="media">
                    <div className="media-left">
                        <a href="#">
                            <img className="media-object" src={src} alt="..."/>
                        </a>
                    </div>
                    <div className="media-body">
                        <h4 className="media-heading">{this.props.item.name}</h4>
                        <div className="item-description">
                            {this.props.item.description}
                            <div className="item-info">
                                Price: <span className="item-price">{this.props.item.price}€</span>
                                <div className="add-to-cart">
                                    <button disabled={disabled} onClick={this.removeFromCart} className="btn btn-default">
                                        {cartMessage}
                                        <span className="glyphicon glyphicon-shopping-cart"/>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

class ItemList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            items: [],
            errorMessage: 'No items for now...'
        }
    }

    componentDidMount () {

        $.get('/session', (results) => {
            this.setState({
                userId: results
            });
            $.get('/api/items', (results) => {
                this.setState({
                    items: results,
                    errorMessage: ''
                })
            });
            $.get('/api/cart/'+this.state.userId, (results) => {
                let cartEntryItems = results.map(i => i.itemName);
                this.setState({
                    userCartItems: cartEntryItems
                })
            });
        })
    }

    render () {
        var index = 0;
        let filteredItems = this.state.items.filter((item) => {
            return $.inArray(item.name, this.state.userCartItems) > -1;
        });
        let items = filteredItems.map((item) => {
            let added = $.inArray(item.name, this.state.userCartItems) > -1;
            index += 1;
            return <Item key={item.name} item={item} added={added} num={index} userId={this.state.userId} />;
        });

        return (
            <div>
                <span>{this.state.errorMessage}</span>
                <div id="item-list" class="items-list">
                    {items}
                </div>
            </div>
        )
    }
}

class Cart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    render () {
        return (
            <ItemList/>
        )
    }
}

render(<Cart/>, document.getElementById('items'));
