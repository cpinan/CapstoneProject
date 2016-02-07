package com.carlospinan.lolguide.storage;

import android.test.AndroidTestCase;

import com.carlospinan.lolguide.ApplicationController;
import com.carlospinan.lolguide.data.Globals;
import com.carlospinan.lolguide.data.models.mock.Dog;
import com.carlospinan.lolguide.data.models.mock.Person;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * @author Carlos Piñan
 */
public class RealmTesting extends AndroidTestCase {

    private Realm realm;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ApplicationController.setContext(getContext());
        realm = Realm.getInstance(getContext());
    }

    public void testRealmStorage() {
        realm.beginTransaction();
        Dog myDog = realm.createObject(Dog.class);
        myDog.setName("Fido");
        myDog.setAge(1);
        realm.commitTransaction();
        assertNotNull(myDog);

        Dog myPuppy = realm.where(Dog.class).equalTo("age", 1).findFirst();
        realm.beginTransaction();
        myPuppy.setAge(2);
        realm.commitTransaction();
        assertNotNull(myPuppy);

        assertEquals(myPuppy.getAge(), 2);
        Globals.testLog(myPuppy.getName());
        Globals.testLog(String.valueOf(myPuppy.getAge()));
        realm.beginTransaction();
        realm.allObjects(Dog.class).clear();
        realm.commitTransaction();
    }

    public void testRealmLists() {

        RealmList<Dog> dogs = new RealmList<>();
        int i;
        realm.beginTransaction();
        for (i = 1; i <= 10; i++) {
            Dog myDog = realm.createObject(Dog.class);
            myDog.setName("Doggie " + i);
            myDog.setAge(i);
            dogs.add(myDog);
        }
        realm.commitTransaction();

        assertTrue(dogs.size() > 0);

        realm.beginTransaction();
        Person person = realm.createObject(Person.class);
        person.setName("Carlos");
        person.setImageUrl("http;//google");
        person.setDogs(dogs);
        assertNotNull(person);
        realm.commitTransaction();

        RealmResults<Person> persons = realm.where(Person.class).findAll();
        assertNotNull(persons);
        assertTrue(persons.size() > 0);

        Person p = persons.get(0);
        assertNotNull(p);
        assertNotNull(p.getDogs());
        assertTrue(p.getDogs().size() > 0);

        realm.beginTransaction();
        realm.allObjects(Dog.class).clear();
        realm.allObjects(Person.class).clear();
        realm.commitTransaction();

    }

    public void testBasicRealm() {
        // Use them like regular java objects
        Dog dog = new Dog();
        dog.setName("Rex");
        dog.setAge(1);
        Globals.testLog("Name of the dog: " + dog.getName());

        // Query Realm for all dogs less than 2 years old
        RealmResults<Dog> puppies = realm.where(Dog.class).lessThan("age", 2).findAll();
        puppies.size(); // => 0 because no dogs have been added to the Realm yet
        assertTrue(puppies.size() == 0);

        // Persist your data easily
        realm.beginTransaction();
        realm.copyToRealm(dog);
        realm.commitTransaction();

        // Queries are updated in real time
        puppies.size(); // => 1
        assertTrue(puppies.size() == 1);
        realm.allObjects(Dog.class).clear();
    }

    private void asyncRealmExample() {
//        // Query and update the result asynchronously in another thread
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                // begin & end transcation calls are done for you
//                Dog theDog = realm.where(Dog.class).equals("age", 1).findFirst();
//                theDog.setAge(3);
//            }
//        }, new Realm.Transaction.Callback() {
//            @Override
//            public void onSuccess() {
//                // Original Queries and Realm objects are automatically updated.
//                puppies.size(); // => 0 because there are no more puppies (less than 2 years old)
//                dog.getAge();   // => 3 the dogs age is updated
//            }
//        });
    }
}
