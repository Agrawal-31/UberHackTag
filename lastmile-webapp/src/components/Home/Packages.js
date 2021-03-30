import React, { useEffect, useState } from "react";
import axios from "axios";
import { API_BASE_URL, ACCESS_TOKEN_NAME } from "../../constants/apiConstants";
import { Card, Alert, Row, Col, Container } from "react-bootstrap";
import setDefaultAxios from "../../utils/setDefaultAxios";
import Loading from "../Loading/Loading";
export default function ViewPackage() {
  const [packages, setPackages] = useState(null);

  function updatePackages() {
    axios
      .get(API_BASE_URL + "/packages/")
      .then(function (response) {
        const data = response.data;
        setPackages(data);
      })
      .catch(function (error) {
      })
  }

  async function checkUser() {
    try {
      setDefaultAxios(localStorage.getItem(ACCESS_TOKEN_NAME));
    } catch (err) {}
  }

  useEffect(() => {
    checkUser().then(() => updatePackages());
  }, []);

  const RenderPackageList = () => {
    if (!packages) {
      return <Loading />;
    }

    const p = [];
    if (packages.length === 0) {
      p.push(<Alert variant="primary">No Packages yet.</Alert>);
      return p;
    }

    packages.forEach((id) => {
      p.push(
        <>
          <Card
            bg="secondary"
            text="white"
            body
            className="p-1 mt-4 align-items-center justify-content-center"
            key={id.packageId}
          >
            <Row>
              <Col md="auto">
                <h4> Package Number</h4>
                <br />
                <h5>Hub Location</h5>
                <br />
                <h5> Delivery Location</h5>
                <br />
                <h5> Package Value</h5>
                <br />
              </Col>
              <Col md="auto">
                <h4> {id.packageId}</h4>
                <br />
                <h5> {id.hubLocation}</h5>
                <br />
                <h5> {id.deliveryLocation}</h5>
                <br />
                <h5> {id.packageValue}</h5>
                <br />
              </Col>
            </Row>
          </Card>
        </>
      );
    });
    return (
      <>
        {p}
        <hr />
      </>
    );
  };
  return (
    <div className="w-40 d-flex align-items-center justify-content-center">
      <Container className="align-items-center justify-content-center">
        <h2 style={{ color: "white" }} className="text-center mt-5 mb-5">
          View Packages
        </h2>
        {RenderPackageList()}
      </Container>
    </div>
  );
}
