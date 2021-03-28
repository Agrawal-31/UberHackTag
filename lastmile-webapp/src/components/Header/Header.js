import React from "react";
import { withRouter } from "react-router-dom";
import { ACCESS_TOKEN_NAME } from "../../constants/apiConstants";
import {Button, Navbar} from "react-bootstrap";

function Header(props) {
  const capitalize = (s) => {
    if (typeof s !== "string") return "";
    return s.charAt(0).toUpperCase() + s.slice(1);
  };
  let title = capitalize(
    props.location.pathname.substring(1, props.location.pathname.length)
  );
  if (props.location.pathname === "/") {
    title = "Welcome";
  }
  function renderLogout() {
    if (props.location.pathname === "/home" || props.location.pathname === "/packages") {
      return (
        <div className="ml-3">
          <Button variant = "danger" onClick={() => handleLogout()}>
            Logout
          </Button>
        </div>
      );
    }
  }
  function handleLogout() {
    localStorage.removeItem(ACCESS_TOKEN_NAME);
    props.history.push("/login");
  }
  function handleViewPackages(){
    props.history.push("/packages");
  }
  function renderViewPackages() {
    if (props.location.pathname === "/home") {
      return (
          <div className="ml-auto">
            <Button variant="secondary" onClick = {() =>handleViewPackages()}>
              View Packages

            </Button>
          </div>
      );
    }
  }
  return (
  <Navbar bg="dark" variant="dark">
    <Navbar.Brand href="#home">
      Uber Freight
    </Navbar.Brand>
    {renderViewPackages()}
    {renderLogout()}
  </Navbar>
  );
}
export default withRouter(Header);
