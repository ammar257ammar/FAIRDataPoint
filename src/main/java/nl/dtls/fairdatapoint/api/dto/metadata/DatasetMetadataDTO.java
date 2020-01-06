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
package nl.dtls.fairdatapoint.api.dto.metadata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.dtls.fairdatapoint.api.dto.common.BreadcrumbDTO;
import nl.dtls.fairdatapoint.api.dto.membership.MembershipDTO;
import nl.dtls.fairdatapoint.api.dto.uri.UriDTO;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DatasetMetadataDTO {

    protected String identifier;

    protected String uri;

    protected String title;

    protected String description;

    protected String issued;

    protected String modified;

    protected String version;

    protected UriDTO license;

    protected String accessRights;

    protected UriDTO specification;

    protected UriDTO language;

    protected UriDTO publisher;

    protected List<UriDTO> themes;

    protected List<String> keywords;

    protected List<DistributionMetadataSimpleDTO> distributions;

    protected Map<String, BreadcrumbDTO> links;

    protected MembershipDTO membership;

}