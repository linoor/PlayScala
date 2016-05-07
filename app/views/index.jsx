import React from 'react';
import {render} from 'react-dom';

class Item extends React.Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    render () {
        return (
            <div className="col-md-4">
                <div className="media">
                    <div className="media-left">
                        <a href="#">
                            <img className="media-object" src="http://lorempixel.com/64/64/" alt="..."/>
                        </a>
                    </div>
                    <div className="media-body">
                        <h4 className="media-heading">{this.props.item.name}</h4>
                        <div className="item-description">
                            {this.props.item.description}
                            <div className="item-info">
                                <span className="item-price">Price: 5€</span>
                                <div className="add-to-cart">
                                    <span>Add to cart</span>
                                    <span className="glyphicon glyphicon-shopping-cart"/>
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
        $.get('/api/items', (results) => {
            this.setState({
                items: results,
                errorMessage: ''
            })
        })
    }

    render () {
        let items = this.state.items.map((item) => <Item item={item}/>);

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

render(<ItemList/>, document.getElementById('item-list'));