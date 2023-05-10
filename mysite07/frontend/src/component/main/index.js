import React from 'react';
import MySiteLayout from "../../layout/MySiteLayout";
import styles from '../../assets/scss/component/main/Main.scss';
import Container from './Container';

export default function Main() {
    return (
        <MySiteLayout>
            <div className={styles.Main}>
                <Container />
            </div>
        </MySiteLayout>
    );
}