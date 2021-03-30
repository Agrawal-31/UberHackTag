import React, { useState } from "react";
import axios from "axios";
import { API_BASE_URL, ACCESS_TOKEN_NAME } from "../../constants/apiConstants";
import { withRouter } from "react-router-dom";
import AlertComponent from "../AlertComponent/AlertComponent";
import banner from "../../banner.png";
import { Card, Col, Image, Row, Form, Button } from "react-bootstrap";

function RegistrationForm(props) {
  const [state, setState] = useState({
    email: "",
    password: "",
    confirmPassword: "",
    successMessage: null,
    errorMessage: null,
  });

  const handleChange = (e) => {
    const { id, value } = e.target;
    setState((prevState) => ({
      ...prevState,
      [id]: value,
    }));
  };

  const hideError = (error) => {
    setState((prevState) => ({
      ...prevState,
      errorMessage: error,
    }));
  };

  const redirectToHome = () => {
    props.updateTitle("Home");
    props.history.push("/home");
  };
  const redirectToLogin = () => {
    props.updateTitle("Login");
    props.history.push("/login");
  };

  const handleSubmitClick = (e) => {
    e.preventDefault();
    sendDetailsToServer();
  };

  const sendDetailsToServer = () => {
    if (state.email.length && state.password.length) {
      props.showError(null);
      const payload = {
        username: state.email,
        password: state.password,
      };
      axios
        .post(API_BASE_URL + "/register", payload)
        .then(function (response) {
          console.log(response);
          if (response.status === 200) {
            setState((prevState) => ({
              ...prevState,
              successMessage:
                "Registration successful. Redirecting to home page..",
            }));
            redirectToHome();
            localStorage.setItem(ACCESS_TOKEN_NAME, response.data.jwt);
            props.showError(null);
          }
        })
        .catch(function (error) {
          console.log(error.response.data.message);
          hideError(error.response.data.message);
        });
    } else {
      props.showError("Please enter valid username and password");
    }
  };
  return (
    <div>
      <Row>
        <Col>
          <Image className="al" src={banner} fluid></Image>
        </Col>

        <Col>
          <Card className="w-100 mt-5" bg="secondary" text="white">
            <Card.Body>
              {state.errorMessage && (
                <AlertComponent
                  errorMessage={state.errorMessage}
                  hideError={hideError}
                />
              )}
              <Form>
                <h2 className="text-center mt-5 mb-5">Register</h2>
                <div className="form-group text-left">
                  <label htmlFor="exampleInputEmail1">
                    <h5>Email</h5>
                  </label>
                  <input
                    type="email"
                    className="form-control"
                    id="email"
                    aria-describedby="emailHelp"
                    placeholder="Enter Username"
                    value={state.email}
                    onChange={handleChange}
                  />
                  <small id="emailHelp" className="form-text text-muted">
                    We'll never share your data with anyone else.
                  </small>
                </div>
                <div className="form-group text-left">
                  <label htmlFor="exampleInputPassword1">
                    <h5>Password</h5>
                  </label>
                  <input
                    type="password"
                    className="form-control"
                    id="password"
                    placeholder="Password"
                    value={state.password}
                    onChange={handleChange}
                  />
                </div>
                <div className="form-group text-left">
                  <label htmlFor="exampleInputPassword1">
                    <h5>Confirm Password</h5>
                  </label>
                  <input
                    type="password"
                    className="form-control"
                    id="confirmPassword"
                    placeholder="Confirm Password"
                    value={state.confirmPassword}
                    onChange={handleChange}
                  />
                </div>
                <Button
                  size="lg"
                  className="w-100"
                  variant="dark"
                  type="submit"
                  onClick={handleSubmitClick}
                >
                  Register
                </Button>
              </Form>
              <div
                className="alert alert-success mt-2"
                style={{ display: state.successMessage ? "block" : "none" }}
                role="alert"
              >
                {state.successMessage}
              </div>
              <div className="mt-2">
                <span>Already have an account? </span>
                <span className="loginText" onClick={() => redirectToLogin()}>
                  Login here
                </span>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </div>
  );
}

export default withRouter(RegistrationForm);
