import React, { useState, useRef } from "react";
import axios from "axios";

import { API_BASE_URL, ACCESS_TOKEN_NAME } from "../../constants/apiConstants";
import Autocomplete from "react-google-autocomplete";
import {
  Image,
  Row,
  Col,
  Card,
  Form,
  InputGroup,
  Button,
  FormControl,
  Alert,
} from "react-bootstrap";
import setDefaultAxios from "../../utils/setDefaultAxios";
import banner from "../../banner.png";
export default function Home() {
  const [deliveryLocation, setDeliveryLocation] = useState(null);
  const [hubLocation, setHubLocation] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);
  const packageIdRef = useRef();
  const packageValueRef = useRef();
  const dateRef = useRef("");
  const [error, setError] = useState("");
  const formRef = useRef();

  async function checkUser() {
    try {
      setDefaultAxios(localStorage.getItem(ACCESS_TOKEN_NAME));
    } catch (err) {}
  }

  function addPackage() {
    if (!deliveryLocation) {
      setError("Enter Delivery Location");
      return;
    }

    if (!hubLocation) {
      setError("Enter Hub Location");
      return;
    }

    const externalPackageId = packageIdRef.current.value;
    const packageValue = packageValueRef.current.value;
    const expectedDeliverydate = dateRef.current.value;
    const deliveryLocationAddress = deliveryLocation.name;

    const hubLocationAddress = hubLocation.name;
    const deliveryLocationID = deliveryLocation.place_id;
    const hubLocationID = hubLocation.place_id;

    const payload = {
      packageValue: packageValue,
      hubLocationId: hubLocationID,
      deliveryLocationId: deliveryLocationID,
      hubLocation: hubLocationAddress,
      deliveryLocation: deliveryLocationAddress,
      expectedDeliveryDate: expectedDeliverydate,
      externalPackageId: externalPackageId,
      hubLattitude: hubLocation.geometry.location.lat(),
      deliveryLattitude: deliveryLocation.geometry.location.lat(),
      hubLongitude: hubLocation.geometry.location.lng(),
      deliveryLongitude: deliveryLocation.geometry.location.lng(),
    };

    axios
      .post(API_BASE_URL + "/packages/", payload)
      .then(function (response) {
        formRef.current.reset();
        setSuccessMessage("Package sucessfully added to platform.");
      })
      .catch(function (error) {});
  }

  async function handleSubmit(e) {
    e.preventDefault();
    checkUser().then(() => addPackage());
  }
  function renderAddPackageForm() {
    return (
      <Card className="mt-5" bg="secondary" text="white">
        <Card.Header>
          <h1 className="text-center">Add Package</h1>
        </Card.Header>
        {error && (
          <Alert className="text-center" variant="danger">
            {error}
          </Alert>
        )}
        <Card.Body>
          <Form onSubmit={handleSubmit} ref={formRef}>
            <InputGroup className="mt-4 mb-4" size="lg">
              <InputGroup.Prepend>
                <InputGroup.Text style={{ width: "180px" }} id="package_id">
                  Package ID
                </InputGroup.Text>
              </InputGroup.Prepend>
              <FormControl type="text" ref={packageIdRef} required />
            </InputGroup>

            <div id="deliveryLocation">
              <InputGroup className="mt-4 mb-4" size="lg">
                <InputGroup.Prepend>
                  <InputGroup.Text
                    style={{ width: "180px" }}
                    id="deliveryLocation"
                  >
                    Delivery Location
                  </InputGroup.Text>
                </InputGroup.Prepend>
                <Autocomplete
                  style={{ width: "315px" }}
                  onPlaceSelected={(place) => {
                    setDeliveryLocation(place);
                  }}
                  componentRestrictions={{ country: "in" }}
                  types={["establishment"]}
                  fields={[]}
                />
              </InputGroup>
            </div>
            <div id="deliveryLocation">
              <InputGroup className="mt-4 mb-4" size="lg">
                <InputGroup.Prepend>
                  <InputGroup.Text style={{ width: "180px" }} id="hubLocation">
                    Hub Location{" "}
                  </InputGroup.Text>
                </InputGroup.Prepend>

                <Autocomplete
                  style={{ width: "315px" }}
                  onPlaceSelected={(place) => {
                    setHubLocation(place);
                  }}
                  componentRestrictions={{ country: "in" }}
                  types={["establishment"]}
                  fields={[]}
                />
              </InputGroup>
            </div>

            <InputGroup className="mt-4 mb-4" size="lg">
              <InputGroup.Prepend>
                <InputGroup.Text style={{ width: "180px" }} id="package_value">
                  Package Value
                </InputGroup.Text>
              </InputGroup.Prepend>
              <Form.Control as="select" ref={packageValueRef} required>
                <option>Low</option>
                <option>Medium</option>
                <option>High</option>
              </Form.Control>
            </InputGroup>

            <InputGroup className="mt-4 mb-4" size="lg">
              <InputGroup.Prepend>
                <InputGroup.Text style={{ width: "180px" }} id="expected_date">
                  Expected Date
                </InputGroup.Text>
              </InputGroup.Prepend>
              <FormControl type="datetime-local" ref={dateRef} required />
            </InputGroup>
            <Button size="lg" className="w-100" variant="dark" type="submit">
              Submit
            </Button>
          </Form>
          <div
            className="alert alert-success mt-2"
            style={{ display: successMessage ? "block" : "none" }}
            role="alert"
          >
            {successMessage}
          </div>
        </Card.Body>
      </Card>
    );
  }
  return (
    <div>
      <Row>
        <Col>
          <Image className="al" src={banner} fluid></Image>
        </Col>
        <Col>{renderAddPackageForm()}</Col>
      </Row>
    </div>
  );
}
