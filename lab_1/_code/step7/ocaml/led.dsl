application led {

    actuator led: 13

    actuator a: 1
    actuator b: 2
    actuator c: 3
    actuator d: 4
    actuator e: 5
    actuator f: 6
    actuator g: 7

    sensor   btn: 10

    -> reset {
        led is LOW

        a is LOW
        b is LOW
        c is LOW
        d is LOW
        e is LOW
        f is LOW
        g is HIGH

        if btn is HIGH goto reset
        goto one
    }

    one {
        led is HIGH

        a is HIGH
        b is LOW
        c is LOW
        d is HIGH
        e is HIGH
        f is HIGH
        g is HIGH

        if btn is HIGH goto reset
        goto two
    }

    two {
        led is LOW

        a is LOW
        b is LOW
        c is HIGH
        d is LOW
        e is LOW
        g is LOW

        if btn is HIGH goto reset
        goto three
    }

    three {
        led is HIGH

        a is LOW
        b is LOW
        c is LOW
        e is HIGH

        if btn is HIGH goto reset
        goto four
    }

    four {
        led is LOW

        a is HIGH
        b is LOW
        f is LOW

        if btn is HIGH goto reset
        goto five
    }

    five {
        led is HIGH

        a is LOW
        b is HIGH

        if btn is HIGH goto reset
        goto six
    }

    six {
        led is LOW

        a is LOW
        b is HIGH

        if btn is HIGH goto reset
        goto seven
    }

    seven {
        led is HIGH

        a is LOW
        b is LOW


        if btn is HIGH goto reset
        goto eight
    }

    eight {
        led is LOW

        a is LOW
        b is LOW


        if btn is HIGH goto reset
        goto nine
    }

    nine {
        led is HIGH

        a is LOW
        b is LOW

        goto reset
    }

}
