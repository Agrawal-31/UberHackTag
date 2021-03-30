import React from "react";
import { Spinner } from "react-bootstrap";

export default function Loading() {
    return (
        <div style={{color: 'white'}} className="loading">
            <p className="text-center">
                <Spinner animation="border" /> Loading...
            </p>
        </div>
    );
}