import React from 'react';

class User extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            users:
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
                ]
        }
    }

    route = () => {
        this.props.history.push('/contact');
    }

    render() {
        const { params } = this.props.match;

        return (
            <div>
                <h1>Users</h1>
                <p>{params.id}</p>
                <button onClick={this.route}>Go to Contact Page</button>

                <br />

                <table>
                    <tbody>
                        {
                            this.state.users.map((person, i) =>
                                <TableRow key={i} data={person} />
                            )
                        }
                    </tbody>
                </table>

                <br />

                <div>Array: {this.props.propArray}</div>
                <div>Bool: {this.props.propBool ? "True..." : "False..."}</div>
                <div>Func: {this.props.propFunc(3)}</div>
                <div>Number: {this.props.propNumber}</div>
                <div>String: {this.props.propString}</div>
                <div>Object: {this.props.propObject.objectName1}</div>
                <div>Object: {this.props.propObject.objectName2}</div>
                <div>Object: {this.props.propObject.objectName3}</div>
            </div>
        )
    }
}

/*
User.propTypes = {
    propArray: React.PropTypes.array.isRequired,
    propBool: React.PropTypes.bool.isRequired,
    propFunc: React.PropTypes.func,
    propNumber: React.PropTypes.number,
    propString: React.PropTypes.string,
    propObject: React.PropTypes.object
}
*/

User.defaultProps = {
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

export default User;
