import React, { Component } from 'react';
import './App.css';
import { TopLevel } from './pages/TopLevel';
import { BrowserRouter as Router, withRouter } from "react-router-dom"

export default class App extends Component {
    render() {
        return (
            <Router><TopLevel /></Router>)
    }
}
