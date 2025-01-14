/**
 * The MIT License
 * Copyright © 2017 DTL
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package nl.dtls.fairdatapoint.database.mongo.migration.development.membership.data;

import nl.dtls.fairdatapoint.database.mongo.migration.development.resource.data.ResourceDefinitionFixtures;
import nl.dtls.fairdatapoint.entity.membership.Membership;
import nl.dtls.fairdatapoint.entity.membership.MembershipPermission;
import nl.dtls.fairdatapoint.util.KnownUUIDs;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MembershipFixtures {

    public static final MembershipPermission READ = new MembershipPermission(1 << 0, 'R'); // 1
    public static final MembershipPermission WRITE = new MembershipPermission(1 << 1, 'W'); // 2
    public static final MembershipPermission CREATE = new MembershipPermission(1 << 2, 'C'); // 4
    public static final MembershipPermission DELETE = new MembershipPermission(1 << 3, 'D'); // 8
    public static final MembershipPermission ADMINISTRATION = new MembershipPermission(1 << 4, 'A'); // 16

    public Membership owner() {
        return new Membership(
                KnownUUIDs.MEMBERSHIP_OWNER_UUID,
                "Owner",
                new ArrayList<>() {{
                    add(WRITE);
                    add(DELETE);
                    add(CREATE);
                    add(ADMINISTRATION);
                }},
                new ArrayList<>() {{
                    add(KnownUUIDs.RD_CATALOG_UUID);
                    add(KnownUUIDs.RD_DATASET_UUID);
                    add(KnownUUIDs.RD_DISTRIBUTION_UUID);
                }}
        );
    }

    public Membership dataProvider() {
        return new Membership(
                KnownUUIDs.MEMBERSHIP_DATAPROVIDER_UUID,
                "Data Provider",
                new ArrayList<>() {{
                    add(CREATE);
                }},
                new ArrayList<>() {{
                    add(KnownUUIDs.RD_CATALOG_UUID);
                }}
        );
    }

}
