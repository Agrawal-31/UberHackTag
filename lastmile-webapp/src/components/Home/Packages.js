import React, {useEffect, useState} from "react";
import axios from "axios";
import {API_BASE_URL,ACCESS_TOKEN_NAME} from "../../constants/apiConstants";
import {Card, Alert, Row, Col, Badge, Container} from "react-bootstrap"
export default function ViewPackage() {


    const packageList = [
        {
            "packageId": 19,
            "packageValue": "5000",
            "hubLocationId": "13",
            "deliveryLocationId": "42",
            "hubLocation": "Gaya",
            "deliveryLocation": "Patna"
        },
        {
            "packageId": 20,
            "packageValue": "5000",
            "hubLocationId": "13",
            "deliveryLocationId": "42",
            "hubLocation": "Gaya",
            "deliveryLocation": "Patna"
        }
    ]

    const RenderPackageList = () =>{
    if(!packageList){
        return <h1>Loading</h1>
    }

        const p = [];
            console.log(packageList.length)
        if (packageList.length === 0) {

            p.push(<Alert variant="primary">No Packages yet.</Alert>);
            return p;
        }

        packageList.forEach((id) =>{
            p.push(
                <>
                    <Card body className="p-1 mt-4 align-items-center justify-content-center"  key = {id.packageId}>
                        <Row>
                            <Col md="auto">
                               <h4 > Package Number {id.packageId}</h4>
                                <br/>
                                <h5 >Hub Location {id.hubLocation}</h5>
                                <br/>
                                <h5> Delivery Location  {id.deliveryLocation}</h5>
                                <br/>
                                <h5> Package Value {id.packageValue}</h5>
                                <br/>
                                <hr />
                            </Col>
                        </Row>
                    </Card>
                </>
            )
        })
        return (
            <>
                {p}
            </>
        );

    }
    return (
        <div className= "w-40 d-flex align-items-center justify-content-center">


            <Container className= "align-items-center justify-content-center" >

                <h2 className="text-center mt-4">View Packages</h2>
                {RenderPackageList()}
            </Container>
            </div>


    );
}
