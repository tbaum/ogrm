package org.ogrm.util;

import java.util.List;

import com.bsc.commons.functors.transformer.Transformer;

public class ChainOfCommand<IN, OUT> implements Transformer<IN, OUT> {

	private List<Transformer<IN, OUT>> chain;

	public ChainOfCommand() {
		chain = Lists.newList();
	}

	public ChainOfCommand<IN,OUT> add( Transformer<IN, OUT> transformers ) {
		chain.add( transformers );
		return this;
	}

	@Override
	public OUT transform( IN input ) {
		for (Transformer<IN, OUT> t : chain) {
			OUT out = t.transform( input );
			if (out != null)
				return out;
		}
		return null;
	}

}
