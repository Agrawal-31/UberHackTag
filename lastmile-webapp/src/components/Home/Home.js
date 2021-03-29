import React, { useEffect, useState, useRef } from "react";
import axios from "axios";

import {
  API_BASE_URL,
  ACCESS_TOKEN_NAME,
  GOOGLE_MAP_API_KEY,
} from "../../constants/apiConstants";
import Autocomplete from "react-google-autocomplete";
import { Card, Form, InputGroup, Button, FormControl } from "react-bootstrap";
import Geosuggest from "react-geosuggest";
import setDefaultAxios from "../../utils/setDefaultAxios";
export default function Home() {
  const [deliveryLocation, setDeliveryLocation] = useState(null);
  const [hubLocation, setHubLocation] = useState(null);
  const packageAmountRef = useRef(0);

  const [packages, SetPackages] = useState();

  function updatePackages() {
    axios
      .get(
        API_BASE_URL + "/packages/"
      )
      .then(function (response) {
        console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      })
      .then(function () {
        // always executed
      });
  }

  async function checkUser() {
    try {
      setDefaultAxios(localStorage.getItem(ACCESS_TOKEN_NAME));
    } catch (err) {}
  }

  useEffect(() => {
    checkUser().then(() => updatePackages());
  }, []);
  return (
    <div>
      <Geosuggest />
      {/*<h1>Add Packages</h1>*/}
      <h2>{packages}</h2>
      <Card className="mt-5">
        <Card.Header>
          <h1>Add Package</h1>
        </Card.Header>
        <Card.Body>
          <Form>
            <InputGroup className="mt-4 mb-4" size="lg">
              <InputGroup.Prepend>
                <InputGroup.Text style={{ width: "180px" }} id="package_id">
                  Package ID
                </InputGroup.Text>
              </InputGroup.Prepend>
              <FormControl></FormControl>
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
                  style={{ width: "300px" }}
                  onPlaceSelected={(place) => {
                    setDeliveryLocation(place);
                  }}
                  componentRestrictions={{ country: "in" }}
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
                  style={{ width: "300px" }}
                  onPlaceSelected={(place) => {
                    setHubLocation(place);
                  }}
                />
              </InputGroup>
            </div>

            <InputGroup className="mt-4 mb-4" size="lg">
              <InputGroup.Prepend>
                <InputGroup.Text style={{ width: "180px" }} id="package_value">
                  Package Value
                </InputGroup.Text>
              </InputGroup.Prepend>
              <Form.Control as="select">
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
              <FormControl type="datetime-local"></FormControl>
            </InputGroup>
          </Form>
          <Button variant="secondary" type="submit">
            Submit
          </Button>
        </Card.Body>
      </Card>
    </div>
  );
}
