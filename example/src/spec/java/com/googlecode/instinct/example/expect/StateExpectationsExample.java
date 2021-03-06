package com.googlecode.instinct.example.expect;

import static com.googlecode.instinct.expect.Expect.expect;
import com.googlecode.instinct.expect.state.StateExpectations;
import static com.googlecode.instinct.expect.state.matcher.EqualityMatcher.equalTo;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.marker.annotate.Specification;
import java.io.File;
import static java.lang.System.getProperty;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.startsWith;
import org.junit.runner.RunWith;

/**
 * The examples below illustrate Instinct's state-based expectation API.
 * @see StateExpectations
 */
@SuppressWarnings({"MagicNumber", "unchecked"})
@RunWith(InstinctRunner.class)
public final class StateExpectationsExample {

    @Specification
    public void providesMatchersForMakingAssertionsAboutObjects() {
        expect.that("fred").isEqualTo("fred");
        expect.that("fred").isNotEqualTo("barney");
        expect.that(this).isTheSameInstanceAs(this);
        expect.that("fred").isNotTheSameInstanceAs("barney");
        expect.that(this).isOfType(StateExpectationsExample.class);
        expect.that(this).isNotNull();
        expect.that("fred").matches(startsWith("fr"), containsString("ed"));
        expect.that("fred", equalTo("fred"));
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutClasses() {
        expect.that(String.class).isTypeCompatibleWith(Comparable.class);
        expect.that(Comparable.class).isNotTypeCompatibleWith(String.class);
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutStrings() {
        expect.that("andersdabeerz").isEqualToIgnoringCase("AndersDaBeerz");
        expect.that("andersdabeerz").startsWith("anders");
        expect.that("andersdabeerz").containsString("da");
        expect.that("andersdabeerz").endsWith("beerz");
        expect.that("andersdabeerz").isEqualToIgnoringWhiteSpace(" andersdabeerz ");
        expect.that("andersdabeerz").doesNotContainString("water");
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutComparables() {
        expect.that(1).isEqualTo(1);
        expect.that(1).isGreaterThan(0);
        expect.that(1).isGreaterThanOrEqualTo(1);
        expect.that(1).isLessThan(2);
        expect.that(1).isLessThanOrEqualTo(2);
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutCollections() {
        final List<String> people = new ArrayList<String>();
        expect.that(people).isEmpty();
        people.add("fred");
        people.add("wilma");
        expect.that(people).isNotEmpty();
        expect.that(people).isOfSize(2);
        expect.that(people).containsItems("fred", "wilma");
        expect.that(people).containsItem("fred");
        expect.that(people).doesNotContainItems("barney", "betty");
        expect.that(people).hasTheSameContentAs("fred", "wilma");
        expect.that(people).hasTheSameContentAs(asList("fred", "wilma"));
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutIterables() {
        final Iterable<String> people = asList("fred", "wilma");
        expect.that(people).containsItems("fred", "wilma");
        expect.that(people).containsItem("fred");
        expect.that(people).doesNotContainItems("barney", "betty");
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutArrays() {
        final String[] greetings = {"hi", "there"};
        expect.that(greetings).isOfSize(2);
        expect.that(greetings).containsItem("hi");
        expect.that(greetings).doesNotContainItem("bye");
        expect.that(greetings).doesNotContainItem(greaterThan("zip"));
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutMaps() {
        final Map<String, String> map = new HashMap<String, String>();
        expect.that(map).isEmpty();
        map.put("key", "value");
        expect.that(map).isNotEmpty();
        expect.that(map).isOfSize(1);
        expect.that(map).containsKey("key");
        expect.that(map).containsValue("value");
        expect.that(map).containsEntry("key", "value");
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutBooleans() {
        expect.that(true).isTrue();
        expect.that(false).isFalse();
        expect.that(true).isGreaterThan(false);
        expect.that(false).isLessThanOrEqualTo(false);
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutDoubles() {
        expect.that(1.1).isCloseTo(1.0, 0.11);
        expect.that(1.1).isNotCloseTo(1.0, 0.1);
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutEvents() {
        final Object object = new Object();
        final EventObject myEventObject = new MyEventObject(object);
        expect.that(myEventObject).isAnEventFrom(MyEventObject.class, object);
        expect.that(myEventObject).isAnEventFrom(object);
        expect.that(myEventObject).isNotAnEventFrom(new Object());
    }

    @Specification
    public void providesMatchersForMakingAssertionsAboutFiles() {
        final File javaHomeDirectory = new File(getProperty("java.home"));
        expect.that(javaHomeDirectory).exists();
        final File nonExistentDirectory = new File("foo/bar/baz");
        expect.that(nonExistentDirectory).doesNotExist();
    }

    // TODO This needs some work, they're not sensible in what they do currently...
    @Specification
    public void providesMatchersForMakingAssertionsAboutNodes() {
//        final Node fooNode = getRootNode("<foo><bar baz=\"qux\"/></foo>");
//        expect.that(fooNode).matchesXPath("//foo");
//        expect.that(fooNode).matchesXPath("//foo", equalTo(""));
//        expect.that(fooNode).doesNotMatchXPath("//foo/bar/baz");
//        expect.that(fooNode).matchesXPath("//foo/bar['baz']", equalTo("qux"));
    }

    private static final class MyEventObject extends EventObject {
        private static final long serialVersionUID = -2001716596031438536L;

        private MyEventObject(final Object o) {
            super(o);
        }
    }
}
