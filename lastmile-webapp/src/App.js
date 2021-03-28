import React, { useState } from "react";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

import Header from "./components/Header/Header";
import LoginForm from "./components/LoginForm/LoginForm";
import RegistrationForm from "./components/RegistrationForm/RegistrationForm";
import PrivateRoute from "./utils/privateroute";
import Home from "./components/Home/Home";
import 'bootstrap/dist/css/bootstrap.min.css';
import ViewPackage from "./components/Home/Packages";

function App() {
  const [title, updateTitle] = useState(null);
  const [errorMessage, updateErrorMessage] = useState(null);
  return (
    <Router>
      <div className="App">
        <Header title={title} />
        <div className="container d-flex align-items-center flex-column">
          <Switch>
            <Route path="/" exact={true}>
              <RegistrationForm
                showError={updateErrorMessage}
                updateTitle={updateTitle}
              />
            </Route>
            <Route path="/register">
              <RegistrationForm
                showError={updateErrorMessage}
                updateTitle={updateTitle}
              />
            </Route>
            <Route path="/login">
              <LoginForm
                showError={updateErrorMessage}
                updateTitle={updateTitle}
              />
            </Route>
            <PrivateRoute path="/home">
              <Home />
            </PrivateRoute>
            <Route path ="/packages">
              <ViewPackage/>
            </Route>
          </Switch>
        </div>
      </div>
    </Router>
  );
}

export default App;
