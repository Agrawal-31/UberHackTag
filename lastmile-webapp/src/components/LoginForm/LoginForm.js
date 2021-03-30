import React, { useState } from "react";
import axios from "axios";
import { API_BASE_URL, ACCESS_TOKEN_NAME } from "../../constants/apiConstants";
import { withRouter } from "react-router-dom";
import AlertComponent from "../AlertComponent/AlertComponent";
import { Button, Card, Row, Col, Form, Image } from "react-bootstrap";
import banner from "../../banner.png";
function LoginForm(props) {
  const [state, setState] = useState({
    email: "",
    password: "",
    successMessage: null,
    errorMessage: null,
  });
  const hideError = (error) => {
    setState((prevState) => ({
      ...prevState,
      errorMessage: error,
    }));
  };
  const handleChange = (e) => {
    const { id, value } = e.target;
    setState((prevState) => ({
      ...prevState,
      [id]: value,
    }));
  };

  const handleSubmitClick = (e) => {
    e.preventDefault();
    const payload = {
      username: state.email,
      password: state.password,
    };
    axios
      .post(API_BASE_URL + "/authenticate", payload)
      .then(function (response) {
        if (response.status === 200) {
          setState((prevState) => ({
            ...prevState,
            successMessage: "Login successful. Redirecting to home page..",
          }));
          localStorage.setItem(ACCESS_TOKEN_NAME, response.data.token);
          redirectToHome();
        }
      })
      .catch(function (error) {
        hideError(error.response.data.message);
      });
  };
  const redirectToHome = () => {
    props.updateTitle("Home");
    props.history.push("/home");
  };
  const redirectToRegister = () => {
    props.history.push("/register");
    props.updateTitle("Register");
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
                <h2 className="text-center mt-5 mb-5">Login</h2>
                <div className="form-group text-left">
                  <label htmlFor="exampleInputEmail1">
                    <h5>Email</h5>
                  </label>
                  <input
                    type="email"
                    className="form-control"
                    id="email"
                    aria-describedby="emailHelp"
                    placeholder="Email"
                    value={state.email}
                    onChange={handleChange}
                  />
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
                <div className="form-check"></div>
                <Button
                  size="lg"
                  className="w-100"
                  variant="dark"
                  type="submit"
                  onClick={handleSubmitClick}
                >
                  Login
                </Button>
              </Form>
              <div
                className="alert alert-success mt-2"
                style={{ display: state.successMessage ? "block" : "none" }}
                role="alert"
              >
                {state.successMessage}
              </div>
              <div className="mt-4 registerMessage">
                <h6>
                  <span>Dont have an account? </span>
                  <span
                    className="loginText"
                    onClick={() => redirectToRegister()}
                  >
                    Register
                  </span>
                </h6>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </div>
  );
}

export default withRouter(LoginForm);
