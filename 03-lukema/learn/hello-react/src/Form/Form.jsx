import React from 'react';

class Form extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            data1: 'Type text here...',
            data2: 'Initial text here...'
        }
        this.updateState1 = this.updateState1.bind(this);
        this.updateState2 = this.updateState2.bind(this);
    }

    componentDidMount() {
        console.log('Component DID MOUNT!')
    }

    updateState1(e) {
        this.setState({ data1: e.target.value });
    }

    updateState2(e) {
        this.setState({ data2: e.target.value });
    }

    render() {
        return (
            <div>
                <input type="text" value={this.state.data1}
                    onChange={this.updateState1} />
                <h4>{this.state.data1}</h4>

                <br />

                <Content myDataProp={this.state.data2} updateStateProp={this.updateState2}></Content>
            </div>
        );
    }
}

class Content extends React.Component {
    render() {
        return (
            <div>
                <input type="text" value={this.props.myDataProp}
                    onChange={this.props.updateStateProp} />
                <h3>{this.props.myDataProp}</h3>
            </div>
        );
    }
}

export default Form;