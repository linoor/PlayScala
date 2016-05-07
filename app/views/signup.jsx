import React from 'react';
import {render} from 'react-dom';

class SignupForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    render () {
        return (
            <form>
                <button>Submit</button>
            </form>
        )
    }
}

render(<SignupForm/>, document.getElementById('signup'));
