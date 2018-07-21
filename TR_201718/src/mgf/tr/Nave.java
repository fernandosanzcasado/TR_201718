/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mgf.tr;

import java.awt.Color;
import java.awt.Graphics2D;
import kp.jngg.input.InputEvent;
import kp.jngg.input.InputId;
import kp.jngg.input.Keycode;
import kp.jngg.math.Vector2;
import kp.jngg.sprite.Sprite;

/**
 *
 * @author ferna
 */
public class Nave {

    /**
     * Funciones recomendadas: draw: Para dibujar update: Actualizar valores
     * dispatchEvents: capturar eventos de inputs
     */
    private static final double X_SPEED = 6.5;
    private static final double FRICTION = 0.75;

    private final Vector2 position;
    private final Vector2 size;
    private final Vector2 speed;
    private Sprite sprite1;
    private Sprite sprite2;
    private Sprite sprite3;
    private Proyectil shoot;

    private int moveX;
    
    Proyectil shooot = new Proyectil();

    public Nave() {
        position = new Vector2();
        size = new Vector2();
        speed = new Vector2();
        sprite1 = null;
        sprite2 = null;
        sprite3 = null;
    }

    public void setPosition(double x, double y) {
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        position.set(x, y);
    }

    public void setSpeed(double x, double y) {
        speed.x = x;
        speed.y = y;
    }

    public Vector2 getSpeed() {
        return speed.copy();
    }

    public void setSize(double width, double height) {
        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }
        size.set(width, height);
    }

    public void setSprite(Sprite s1, Sprite s2, Sprite s3) {
        
        sprite1 = s1;
        sprite2 = s2;
        sprite3 = s3;

    }

    public void draw(Graphics2D g) {
        if (sprite1 != null && moveX < 0) {
            sprite1.draw(g, position.x, position.y, size.x, size.y);
        }
        if (sprite2 != null && moveX == 0) {
            sprite2.draw(g, position.x, position.y, size.x, size.y);
        }
        if (sprite3 != null && moveX > 0) {
            sprite3.draw(g, position.x, position.y, size.x, size.y);
        }
        drawSpecs(g);
    }

    private void drawSpecs(Graphics2D g) {
        Color old = g.getColor();
        g.setColor(Color.GREEN);
        g.drawString("Position = " + position, 12, 12);
        g.drawString("Speed = " + speed, 12, 24);
        g.setColor(old);
    }

    public void update(double delta) {

        if (position.x < 0) {
            position.x = 0;
        } else if ((position.x + size.x) > 1280) {
            position.x = 1280 - size.x;
        }

        if (moveX != 0) {
            speed.x = X_SPEED * moveX;
        } else {
            if (speed.x > 0) {
                speed.x -= FRICTION;
                if (speed.x < 0) {
                    speed.x = 0;
                }
            } else if (speed.x < 0) {
                speed.x += FRICTION;
                if (speed.x > 0) {
                    speed.x = 0;
                }
            }
        }
        speed.ensureRangeLocal(10, 10);

        position.add(speed);

    }

    public void dispatch(InputEvent event) {
        if (event.getIdType() == InputId.KEYBOARD_TYPE) {

            int code = event.getCode();

            if (code == Keycode.VK_LEFT) {
                moveX += event.isPressed() ? -1 : 1;
            }
            if (code == Keycode.VK_RIGHT) {
                moveX += event.isPressed() ? 1 : -1;
            }
            if (code == Keycode.VK_SPACE) {
                position.y = size.y  /*+ la posicion y de la nave*/;
                position.x = /*position.x de la nave + size.x/2 de la nave - */(size.x/2);
                shoot.onShow();
            }
        }
    }
}
