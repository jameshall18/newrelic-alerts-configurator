package com.ocado.pandateam.newrelic.api.internal;

import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import com.ocado.pandateam.newrelic.api.AlertsExternalServiceConditionsApi;
import com.ocado.pandateam.newrelic.api.exception.NewRelicApiException;
import com.ocado.pandateam.newrelic.api.internal.model.AlertsExternalServiceConditionList;
import com.ocado.pandateam.newrelic.api.internal.model.AlertsExternalServiceConditionWrapper;
import com.ocado.pandateam.newrelic.api.model.conditions.external.AlertsExternalServiceCondition;

import java.util.List;

class DefaultAlertsExternalServiceConditionsApi extends ApiBase implements AlertsExternalServiceConditionsApi {

    private static final String CONDITIONS_URL = "/v2/alerts_external_service_conditions";
    private static final String CONDITION_URL = "/v2/alerts_external_service_conditions/{condition_id}.json";
    private static final String CONDITION_POLICY_URL = "/v2/alerts_external_service_conditions/policies/{policy_id}.json";

    DefaultAlertsExternalServiceConditionsApi(NewRelicPageableClient api) {
        super(api);
    }

    @Override
    public List<AlertsExternalServiceCondition> list(int policyId) throws NewRelicApiException {
        HttpRequest request = api.get(CONDITIONS_URL).queryString("policy_id", policyId);
        return api.asObject(request, AlertsExternalServiceConditionList.class).getList();
    }

    @Override
    public AlertsExternalServiceCondition create(int policyId, AlertsExternalServiceCondition condition)
            throws NewRelicApiException {
        RequestBodyEntity request = api.post(CONDITION_POLICY_URL)
                .routeParam("policy_id", String.valueOf(policyId))
                .body(new AlertsExternalServiceConditionWrapper(condition));
        return api.asObject(request, AlertsExternalServiceConditionWrapper.class).getExternalServiceCondition();
    }

    @Override
    public AlertsExternalServiceCondition update(int conditionId, AlertsExternalServiceCondition condition)
            throws NewRelicApiException {
        RequestBodyEntity request = api.put(CONDITION_URL)
                .routeParam("condition_id", String.valueOf(conditionId))
                .body(new AlertsExternalServiceConditionWrapper(condition));
        return api.asObject(request, AlertsExternalServiceConditionWrapper.class).getExternalServiceCondition();
    }

    @Override
    public AlertsExternalServiceCondition delete(int conditionId) throws NewRelicApiException {
        HttpRequest request = api.delete(CONDITION_URL).routeParam("condition_id", String.valueOf(conditionId));
        return api.asObject(request, AlertsExternalServiceConditionWrapper.class).getExternalServiceCondition();
    }
}
