import React from 'react';

class Props extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            header: "Header from props...",
            content: "Content from props..."
        }
    }
    render() {
        return (
            <div>
                <div>
                    <Header headerProp={this.state.header} />
                    <Content contentProp={this.state.content} />
                </div>

                <br />

                <div>
                    <div>Array: {this.props.propArray}</div>
                    <div>Bool: {this.props.propBool ? "True..." : "False..."}</div>
                    <div>Func: {this.props.propFunc(3)}</div>
                    <div>Number: {this.props.propNumber}</div>
                    <div>String: {this.props.propString}</div>
                    <div>Object: {this.props.propObject.objectName1}</div>
                    <div>Object: {this.props.propObject.objectName2}</div>
                    <div>Object: {this.props.propObject.objectName3}</div>
                </div>
            </div>
        );
    }
}

/*
Props.propTypes = {
    propArray: React.PropTypes.array.isRequired,
    propBool: React.PropTypes.bool.isRequired,
    propFunc: React.PropTypes.func,
    propNumber: React.PropTypes.number,
    propString: React.PropTypes.string,
    propObject: React.PropTypes.object
};
*/

Props.defaultProps = {
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
};

class Header extends React.Component {
    render() {
        return (
            <div>
                <h1>{this.props.headerProp}</h1>
            </div>
        );
    }
}

class Content extends React.Component {
    render() {
        return (
            <div>
                <h2>{this.props.contentProp}</h2>
            </div>
        );
    }
}

export default Props;