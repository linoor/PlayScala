import React from 'react';
import {render} from 'react-dom';

class CategoryPicker extends React.Component {
    constructor(props) {
        super(props);
    }

    onChange(e) {
        this.props.changeCategory(e.target.value);
    }

    render() {
        return (
            <div className="row">
                <div class="col-lg-6">
                    <div class="input-group">
                        <p>Choose category:</p>
                        <input onChange={this.onChange.bind(this)} type="text" class="form-control" placeholder="Search for..."/>
                    </div>
                </div>
            </div>
        )
    }
}

class Item extends React.Component {
    constructor(props) {
        super(props);
        this.addToCart = this.addToCart.bind(this);
        this.render = this.render.bind(this);
        this.state = {
            inCart: false
        };
    }

    addToCart() {
        $.post("/api/cart/" + this.props.userId + "/" + this.props.item.name, (results) => {
            this.setState({
                inCart: true
            })
        })
    }

    render() {
        let cartMessage = this.props.added || this.state.inCart ? "In cart" : "Add to cart";
        let disabled = this.props.added || this.state.inCart;
        let src = "http://lorempixel.com/200/320/food/" + this.props.num;

        return (
            <div className="col-md-4">
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
                                <span className="item-price"> {this.props.item.price}€</span>
                                {(() => {
                                        if (this.props.userId != "None") {
                                            return (
                                                <div className="add-to-cart">
                                                    <button disabled={disabled} onClick={this.addToCart}
                                                            className="btn btn-default">
                                                        {cartMessage}
                                                        <span className="glyphicon glyphicon-shopping-cart"/>
                                                    </button>
                                                </div>
                                            )
                                        }
                                    })()}
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
            errorMessage: 'No items for now...',
            category: "",
        }
    }

    changeCategory(newCategory) {
       this.setState({category: newCategory})
    }

    componentDidMount() {
        $.get('/session', (results) => {
            this.setState({
                userId: results
            })
            $.get('/api/items', (results) => {
                this.setState({
                    items: results,
                    errorMessage: ''
                })
            });
            $.get('/api/cart/' + this.state.userId, (results) => {
                let cartEntryItems = results.map(i => i.itemName);
                this.setState({
                    userCartItems: cartEntryItems
                })
            });
        })
    }

    render() {
        var index = 0;
        let items = this.state.items
            .filter(item => this.state.category === '' || item.category === this.state.category)
            .map((item) => {
            let added = $.inArray(item.name, this.state.userCartItems) > -1;
            index += 1;
            return <Item key={item.name} item={item} added={added} num={index} userId={this.state.userId}/>;
        });

        return (
            <div>
                <CategoryPicker category={this.state.category} changeCategory={this.changeCategory.bind(this)} />
                <span>{this.state.errorMessage}</span>
                <div id="item-list" class="items-list">
                    {items}
                </div>
            </div>
        )
    }
}

if (document.getElementById('item-list')) {
    render(<ItemList/>, document.getElementById('item-list'));
}
