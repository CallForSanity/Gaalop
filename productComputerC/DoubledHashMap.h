/*
 * DoubledHashMap.h
 *
 *  Created on: 22.11.2011
 *      Author: christian
 */

#ifndef DOUBLEDHASHMAP_H_
#define DOUBLEDHASHMAP_H_

#include "stdTypes.h"

template <class K, class V>
class DoubledHashMap {
private:
	unordered_map<K,V> keyToValue;
	unordered_map<V,K> valueToKey;
public:
	DoubledHashMap() {}
	virtual ~DoubledHashMap() {}

	void clear() {
		keyToValue.clear();
		valueToKey.clear();
	}

	void setAssociation(K& key, V& value) {
        keyToValue[key] = value;
        valueToKey[value] = key;
	}

	inline V getValue(K& key) {
		return keyToValue[key];
	}

	inline K getKey(V& value) {
		return valueToKey[value];
	}

	inline bool containsKey(K& key) {
		return keyToValue.count(key) == 1;
	}

	inline bool containsValue(V& value) {
		return valueToKey.count(value) == 1;
	}
};

#endif /* DOUBLEDHASHMAP_H_ */
