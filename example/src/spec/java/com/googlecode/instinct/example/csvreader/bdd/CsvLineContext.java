/*
 * Copyright 2006-2007 Tom Adams
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.instinct.example.csvreader.bdd;

import com.googlecode.instinct.example.csvreader.CsvLine;
import static com.googlecode.instinct.expect.Expect.expect;
import static com.googlecode.instinct.expect.state.matcher.EqualityMatcher.equalTo;
import com.googlecode.instinct.integrate.junit4.InstinctRunner;
import com.googlecode.instinct.internal.util.Suggest;
import com.googlecode.instinct.marker.annotate.BeforeSpecification;
import com.googlecode.instinct.marker.annotate.Context;
import com.googlecode.instinct.marker.annotate.Specification;
import com.googlecode.instinct.marker.annotate.Stub;
import com.googlecode.instinct.marker.annotate.Subject;
import org.junit.runner.RunWith;

@RunWith(InstinctRunner.class)
@Context(groups = {"osdc"})
public final class CsvLineContext {
    @Subject private CsvLine csvLine;
    @Stub private String column1;
    @Stub private String column2;
    @Stub private String column3;

    @Suggest("Split this up, no columns, with columns")
    @BeforeSpecification
    public void before() {
        csvLine = new CsvLine(column1, column2, column3);
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Invalid colum index, 0 >= columnIndex < 3")
    public void throwsExceptionWhenTooLowAnIndexIsProvided() {
        csvLine.getColumn(-1);
    }

    @Specification(expectedException = IllegalArgumentException.class, withMessage = "Invalid colum index, 0 >= columnIndex < 3")
    public void throwsExceptionWhenTooHighAnIndexIsProvided() {
        csvLine.getColumn(3);
    }

    @Specification
    public void providesAccessToColumnsByIndex() {
        expect.that(csvLine.getColumn(0)).isEqualTo(column1);
        expect.that(csvLine.getColumn(1)).isEqualTo(column2);
        expect.that(csvLine.getColumn(2)).isEqualTo(column3);
    }

    @Specification
    public void isEqualToObjectsWithTheSameColumns() {
        expect.that(new CsvLine()).isEqualTo(new CsvLine());
        expect.that(new CsvLine("A")).isEqualTo(new CsvLine("A"));
        expect.that(new CsvLine("A", "B")).isEqualTo(new CsvLine("A", "B"));
    }

    @Specification
    public void isNotEqualToObjectsWithTheDifferentColumns() {
        expect.that(new CsvLine()).isNotEqualTo(new CsvLine("A", "B"));
        expect.that(new CsvLine("A", "A")).isNotEqualTo(new CsvLine("A", "B"));
    }

    @Specification
    public void isNotEqualToNull() {
        expect.that(new CsvLine("")).isNotEqualTo(null);
    }

    @SuppressWarnings({"EqualsBetweenInconvertibleTypes"})
    @Specification
    public void isNotEqualToDifferentClasses() {
        final String notACsvLine = "";
        expect.that(new CsvLine("").equals(notACsvLine)).isFalse();
    }

    @Specification
    public void hasAHasCodeThatIsThatOfTheColumns() {
        final String[] columns = {""};
        expect.that(new CsvLine(columns).hashCode()).isEqualTo(columns.hashCode());
    }

    @Specification
    public void providesColumnsAsAString() {
        expect.that(new CsvLine("A", "B")).hasToString(equalTo("[A, B]"));
    }

    @Specification
    public void toStringReturnsAnEmptyStringWhenNoColumns() {
        expect.that(new CsvLine()).hasToString(equalTo("[]"));
    }
}