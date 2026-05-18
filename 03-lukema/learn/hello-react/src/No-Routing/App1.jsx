import React from 'react';
import './App.scss';

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            data:
                [
                    {
                        "id": 1,
                        "name": "Foo",
                        "age": "20"
                    },
                    {
                        "id": 2,
                        "name": "Bar",
                        "age": "30"
                    },
                    {
                        "id": 3,
                        "name": "Baz",
                        "age": "40"
                    }
                ],
            counter: 0,
            text: 'Input text here...'
        }

        this.setNewNumber = this.setNewNumber.bind(this);
        this.updateState = this.updateState.bind(this);
    }

    setNewNumber() {
        this.setState({ counter: this.state.counter + 1 })
    }

    updateState(e) {
        this.setState({ text: e.target.value });
    }

    render() {
        return (
            <div className="App">
                <header className="App-header">

                    <div>
                        <ContentText myDataProp={this.state.text}
                            updateStateProp={this.updateState}>
                        </ContentText>
                    </div>

                    <div>
                        <button onClick={this.setNewNumber}>INCREMENT</button>
                        <Content myNumber={this.state.counter}></Content>
                    </div>

                    <div>
                        <h4>{this.props.headerProp}</h4>
                        <h5>{this.props.contentProp}</h5>
                    </div>
                    <div>Edit <code>src/App.js</code> and save to reload.</div>
                    <div>Where is the link of "Learn React"? 111</div>
                    <a
                        href="https://reactjs.org"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        Learn React
                    </a>
                    <div>Where is the link of "Learn React"? 222</div>

                    <MyStyle></MyStyle>

                    <table>
                        <tbody>
                            {this.state.data.map((person, i) => <TableRow key={i}
                                data={person} />)}
                        </tbody>
                    </table>

                    <div>
                        <h3>Array: {this.props.propArray}</h3>
                        <h3>Bool: {this.props.propBool ? "True..." : "False..."}</h3>
                        <h3>Func: {this.props.propFunc(3)}</h3>
                        <h3>Number: {this.props.propNumber}</h3>
                        <h3>String: {this.props.propString}</h3>
                        <h3>Object: {this.props.propObject.objectName1}</h3>
                        <h3>Object: {this.props.propObject.objectName2}</h3>
                        <h3>Object: {this.props.propObject.objectName3}</h3>
                    </div>

                </header>
            </div>
        );
    }
}

/*
App.propTypes = {
  propArray: React.PropTypes.any.isRequired,
  propBool: React.PropTypes.bool.isRequired,
  propFunc: React.PropTypes.func,
  propNumber: React.PropTypes.number,
  propString: React.PropTypes.string,
  propObject: React.PropTypes.object
}
*/

App.defaultProps = {
    propArray: [1, 2, 3, 4, 5],
    propBool: true,
    propFunc: function (e) { return e },
    propNumber: 1,
    propString: "String value...",

    propObject: {
        objectName1: "objectValue1",
        objectName2: "objectValue2",
        objectName3: "objectValue3"
    }
}

class ContentText extends React.Component {
    render() {
        return (
            <div>
                <input type="text" value={this.props.myDataProp}
                    onChange={this.props.updateStateProp} />
                <h5>{this.props.myDataProp}</h5>
            </div>
        );
    }
}

class Content extends React.Component {
    componentWillMount() {
        console.log('Component WILL MOUNT!')
    }
    componentDidMount() {
        console.log('Component DID MOUNT!')
    }
    componentWillReceiveProps(newProps) {
        console.log('Component WILL RECIEVE PROPS!')
    }
    shouldComponentUpdate(newProps, newState) {
        return true;
    }
    componentWillUpdate(nextProps, nextState) {
        console.log('Component WILL UPDATE!');
    }
    componentDidUpdate(prevProps, prevState) {
        console.log('Component DID UPDATE!')
    }
    componentWillUnmount() {
        console.log('Component WILL UNMOUNT!')
    }
    render() {
        return (
            <div>
                <h3>{this.props.myNumber}</h3>
            </div>
        );
    }
}

function MyStyle() {

    /**
     * "All dimensions in React Native are unitless, and represent density-independent pixels."
     * While it doesn't say so explicitly, it seems equivalent to Android's dp measure.
     * 
     * HTML tags always use lowercase tag names, while React components start with Uppercase.
     * 
     * Note − You should use className and htmlFor as XML attribute names instead of class and for. 
     * 
     * Since JSX is JavaScript, identifiers such as class and for are discouraged as XML attribute names. 
     * Instead, React DOM components expect DOM property names such as className and htmlFor, respectively.
     */
    const myStyle = {
        fontSize: 20,
        color: '#FF0000'
    };

    return (
        <div>
            <h1 style={myStyle}>My Style</h1>
        </div>
    );
}

class TableRow extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.data.id}</td>
                <td>{this.props.data.name}</td>
                <td>{this.props.data.age}</td>
            </tr>
        );
    }
}

export default App;
