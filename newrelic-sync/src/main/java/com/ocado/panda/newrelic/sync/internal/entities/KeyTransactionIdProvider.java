package com.ocado.panda.newrelic.sync.internal.entities;


import com.ocado.panda.newrelic.api.NewRelicApi;
import com.ocado.panda.newrelic.api.model.transactions.KeyTransaction;
import com.ocado.panda.newrelic.sync.exception.NewRelicSyncException;

import java.util.Optional;

import static java.lang.String.format;

class KeyTransactionIdProvider implements IdProvider {
    @Override
    public Integer getId(NewRelicApi api, String name) {
        Optional<KeyTransaction> ktOptional = api.getKeyTransactionsApi().getByName(name);
        KeyTransaction kt = ktOptional.orElseThrow(
                () -> new NewRelicSyncException(format("Key transaction %s does not exist", name)));
        return kt.getId();
    }
}
