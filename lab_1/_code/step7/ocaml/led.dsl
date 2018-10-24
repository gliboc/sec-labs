application led {

    actuator led: 13

    -> off {
        led is LOW
        goto on
    }

    on {
        led is HIGH
        goto off
    }

    block {
        led is LOW 
        goto block
    }
}
