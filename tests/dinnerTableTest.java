import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterResolver;

import static org.junit.jupiter.api.Assertions.*;


class dinnerTableTest {

    @Test
     void testGuestNumRead(){
        dinnerTable testTable1 = new dinnerTable("hw1-inst1.txt");
        dinnerTable testTable2 = new dinnerTable("hw1-inst2.txt");

        assertEquals(10, testTable1.getNumOfGuest());
        assertEquals(30,testTable2.getNumOfGuest());
    }

}