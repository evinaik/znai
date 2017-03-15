import React from 'react'

class PresentationRegistry {
    constructor(elementsLibrary, presentationElementHandlers, pageItems) {
        this.elementsLibrary = elementsLibrary
        this.presentationElementHandlers = presentationElementHandlers
        this.numberOfSlides_ = 0
        this.slideComponents = []

        this.registerComponents_(pageItems)
    }

    get numberOfSlides() {
        return this.numberOfSlides_
    }

    isEmpty() {
        return this.numberOfSlides_ === 0
    }

    registerComponents_(items) {
        items.forEach(item => this.registerComponent_(item))
    }

    registerComponent_(item) {
        // TODO stop differentiating custom components and regular ones
        const type = item.componentName ? item.componentName : item.type
        const props = item.componentProps ? item.componentProps : item

        const presentationElementHandler = this.presentationElementHandlers[type]
        if (presentationElementHandler) {
            this.register(presentationElementHandler.component, props, presentationElementHandler.numberOfSlides(props))
        }

        if (item.content) {
            this.registerComponents_(item.content)
        }
    }

    register(component, props, numberOfSlides) {
        const slideComponent = {
            component: component,
            props: props,
            numberOfSlides: numberOfSlides
        }

        this.slideComponents.push(slideComponent)
        this.numberOfSlides_ += numberOfSlides
    }

    componentToRender(slideIdx, onLoad) {
        const {slideComponent, componentIdx} = this.findComponentAndIdx(slideIdx)
        const numberOfSlidesToSkip = this.calcNumberOfSlidesToSkip(componentIdx)
        return <slideComponent.component {...slideComponent.props}
                                         elementsLibrary={this.elementsLibrary}
                                         slideIdx={slideIdx - numberOfSlidesToSkip}
                                         onLoad={onLoad}/>
    }

    calcNumberOfSlidesToSkip(componentIdx) {
        let numberOfSlides = 0
        for (let i = 0; i < componentIdx; i++) {
            numberOfSlides += this.slideComponents[i].numberOfSlides
        }

        return numberOfSlides
    }

    findComponentAndIdx(slideNumber) {
        let startSlideIdx = 0
        for (let componentIdx = 0, len = this.slideComponents.length; componentIdx < len; componentIdx++) {
            const slideComponent = this.slideComponents[componentIdx]
            const endSlideIdx = startSlideIdx + slideComponent.numberOfSlides

            if (slideNumber >= startSlideIdx && slideNumber < endSlideIdx) {
                return {slideComponent, componentIdx}
            }

            startSlideIdx = endSlideIdx
        }

        return {slideComponent: this.slideComponents[this.slideComponents.length - 1],
            componentIdx: this.slideComponents.length - 1}
    }
}

export default PresentationRegistry
