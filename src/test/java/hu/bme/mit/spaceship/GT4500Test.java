package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primary;
  private TorpedoStore secondary;


  @BeforeEach
  public void init(){
    primary = mock(TorpedoStore.class);
    secondary = mock(TorpedoStore.class);
    this.ship = new GT4500(primary, secondary);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primary, times(1)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    
    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_PrimarySuccess() {
    // Arrange
    when(primary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
  }

  @Test
  public void fireTorpedo_Single_TwoFireSuccess() {
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean fire1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean fire2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, fire1);
    assertEquals(true, fire2);
    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_TwoTimesPrimary() {
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);

    // Act
    boolean fire1 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean fire2 = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, fire1);
    assertEquals(true, fire2);
    verify(primary, times(2)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Failure() {
    // Arrange
    when(primary.fire(1)).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(primary, times(1)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_PrimaryEmpty() {
    // Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primary, times(0)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_PrimaryEmpty() {
    when(primary.fire(1)).thenReturn(true);
    boolean fire1 = ship.fireTorpedo(FiringMode.SINGLE);

    when(primary.isEmpty()).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    boolean fire2 = ship.fireTorpedo(FiringMode.SINGLE);
    boolean fire3 = ship.fireTorpedo(FiringMode.SINGLE);
    
    assertEquals(true, fire1);
    assertEquals(true, fire2);
    assertEquals(true, fire3);
    verify(primary, times(1)).fire(1);
    verify(secondary, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_TrySecondBothEmpty() {
    when(primary.fire(1)).thenReturn(true);
    boolean fire1 = ship.fireTorpedo(FiringMode.SINGLE);
    
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);
    boolean fire2 = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(true, fire1);
    assertEquals(false, fire2);
    verify(primary, times(1)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_TryPrimaryBothEmpty() {    
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);

    boolean fire = ship.fireTorpedo(FiringMode.SINGLE);

    assertEquals(false, fire);
    verify(primary, times(0)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_BothEmpty() {
    // Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(primary, times(0)).fire(1);
    verify(secondary, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_NewEnum() {
    boolean result = ship.fireTorpedo(FiringMode.NEW);

    assertEquals(false, result);
  }
}
