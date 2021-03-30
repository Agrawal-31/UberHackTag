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
          <Button  style={{width : '150px'}} variant = "danger" onClick={() => handleLogout()}>
            <h5>Logout</h5>
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
  function handleAddPackages(){
    props.history.push("/home");
  }
  function renderViewPackages() {
    if (props.location.pathname === "/home" ) {
      return (
          <div className="ml-auto">
            <Button style={{width : '170px'}} variant="secondary" onClick = {() =>handleViewPackages()}>
              <h5> View Packages</h5>

            </Button>
          </div>
      );
    }
  }
  function renderAddPackages() {
    if (props.location.pathname === "/packages" ) {
      return (
          <div className="ml-auto">
            <Button style={{width : '170px'}} variant="secondary" onClick = {() =>handleAddPackages()}>
              <h5> Add Packages</h5>

            </Button>
          </div>
      );
    }
  }

  return (
  <Navbar bg="dark" variant="dark">
    <Navbar.Brand href="#home">
      <h3>Uber Freights</h3>
    </Navbar.Brand>
    {renderAddPackages()}
    {renderViewPackages()}
    {renderLogout()}

  </Navbar>
  );
}
export default withRouter(Header);
